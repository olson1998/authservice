package com.olson1998.authdata.domain.service.checkpoint;

import com.olson1998.authdata.domain.model.exception.security.CheckpointRequiredException;
import com.olson1998.authdata.domain.model.exception.security.TenantSecretNotFound;
import com.olson1998.authdata.domain.model.processing.checkpoint.DomainCheckpoint;
import com.olson1998.authdata.domain.model.processing.checkpoint.DomainCheckpointTokenHolder;
import com.olson1998.authdata.domain.port.caching.repository.impl.CheckpointCacheRepository;
import com.olson1998.authdata.domain.port.checkpoint.repository.CheckpointRepository;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointTokenHolder;
import com.olson1998.authdata.domain.port.processing.request.repository.RequestContextHolder;
import com.olson1998.authdata.domain.port.security.stereotype.CheckpointContext;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j

@RequiredArgsConstructor
public class CheckpointService implements CheckpointRepository {

    private final TenantSecretProvider tenantSecretProvider;

    private final RequestContextHolder requestContextHolder;

    private final CheckpointCacheRepository checkpointCacheRepository;

    @Override
    public LinkedList<String> getLogs() {
        var ctx = requestContextHolder.getRequestContext();
        if(ctx instanceof CheckpointContext checkpointContext){
            var token = checkpointContext.getToken();
            return checkpointCacheRepository.getValue(token)
                    .orElseThrow(CheckpointRequiredException::new)
                    .getLogs();
        }else {
            throw new CheckpointRequiredException();
        }
    }

    @Override
    public CheckpointTokenHolder create(Long expireTime, Integer maxUsages) {
        var tid = requestContextHolder.getTenantId();
        var id = requestContextHolder.getId();
        var uid = requestContextHolder.getUserId();
        var tenantSecret = tenantSecretProvider.getTenantSecret(tid).orElseThrow(TenantSecretNotFound::new);
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
        return tokenHolder;
    }

}
