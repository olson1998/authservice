package com.olson1998.authdata.domain.service.processing.checkpoint;

import com.olson1998.authdata.domain.model.processing.checkpoint.DomainCheckpoint;
import com.olson1998.authdata.domain.model.processing.checkpoint.DomainCheckpointTokenHolder;
import com.olson1998.authdata.domain.port.caching.repository.impl.CheckpointCacheRepository;
import com.olson1998.authdata.domain.port.processing.checkpoint.repository.CheckpointRepository;
import com.olson1998.authdata.domain.port.processing.checkpoint.repository.CheckpointTokenHolderMapper;
import com.olson1998.authdata.domain.port.processing.checkpoint.stereotype.CheckpointValues;
import com.olson1998.authdata.domain.port.processing.request.repository.RequestContextHolder;
import com.olson1998.authdata.domain.port.security.TenantSecretProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

@Slf4j

@RequiredArgsConstructor
public class CheckpointService implements CheckpointRepository {

    private static final String CHECKPOINT_KEY_FORMAT = "com.olson1998.authdata.Checkpoint(tid=%s,uid=%s,tmp=%s)";

    private final TenantSecretProvider tenantSecretProvider;

    private final RequestContextHolder requestContextHolder;

    private final CheckpointCacheRepository checkpointCacheRepository;

    private final CheckpointTokenHolderMapper checkpointTokenHolderMapper;

    @Override
    public <B extends CheckpointValues> ResponseEntity<B> create(Long expireTime, Integer maxUsages) {
        var context = requestContextHolder.getLocalThreadContext();
        var tid = context.getTenantId();
        var id = context.getId();
        var uid = context.getUserId();
        var tenantSecret = tenantSecretProvider.getTenantSecret(tid).orElseThrow();
        var checkpoint = new DomainCheckpoint(
                id,
                tid,
                uid,
                expireTime,
                maxUsages
        );
        log.info("created new checkpoint: '{}'", checkpoint.getId());
        var tokenHolder = new DomainCheckpointTokenHolder(checkpoint, tenantSecret.toAlgorithm());
        checkpointCacheRepository.cacheValue(tokenHolder.getCheckpointToken(), checkpoint);
        return checkpointTokenHolderMapper.mapToResponseEntity(tokenHolder);
    }
}
