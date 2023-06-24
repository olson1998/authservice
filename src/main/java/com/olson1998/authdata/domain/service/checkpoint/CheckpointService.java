package com.olson1998.authdata.domain.service.checkpoint;

import com.olson1998.authdata.domain.model.checkpoint.DomainCheckpoint;
import com.olson1998.authdata.domain.model.checkpoint.DomainCheckpointTimestamp;
import com.olson1998.authdata.domain.model.checkpoint.DomainCheckpointTokenHolder;
import com.olson1998.authdata.domain.model.exception.security.CheckpointRequiredException;
import com.olson1998.authdata.domain.port.caching.repository.impl.CheckpointCacheRepository;
import com.olson1998.authdata.domain.port.caching.stereotype.CheckpointTimestamp;
import com.olson1998.authdata.domain.port.checkpoint.repository.CheckpointRepository;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointTokenHolder;
import com.olson1998.authdata.domain.port.processing.request.repository.RequestContextHolder;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.stereotype.CheckpointContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.LinkedList;

@Slf4j

@RequiredArgsConstructor
public class CheckpointService implements CheckpointRepository {

    private static final String CHECKPOINT_CACHE_KEY = "com.olson1998.Checkpoint(tid=%s,uid=%s,pubkey=%s)";

    private final TenantSecretProvider tenantSecretProvider;

    private final RequestContextHolder requestContextHolder;

    private final CheckpointCacheRepository checkpointCacheRepository;

    @Override
    public LinkedList<String> getLogs() {
        var ctx = requestContextHolder.getRequestContext();
        if(ctx instanceof CheckpointContext checkpointContext){
            var token = checkpointContext.getToken();
            var checkpointTimestamp = checkpointCacheRepository.getHashValue(token)
                    .orElseThrow(CheckpointRequiredException::new);
            var tid = checkpointTimestamp.getTenantId();
            var tenantSecret = tenantSecretProvider.getTenantSecret(tid)
                    .orElseThrow(CheckpointRequiredException::new);
            var alg = tenantSecret.toAlgorithm();
            var key = createCheckpointKey(checkpointTimestamp, token);
            return checkpointCacheRepository.getValue(key)
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
        var tenantSecret = tenantSecretProvider.getTenantSecret(tid)
                .orElseThrow();
        var alg = tenantSecret.toAlgorithm();
        var checkpoint = new DomainCheckpoint(
                id,
                tid,
                uid,
                expireTime,
                maxUsages
        );
        var tokenHolder = new DomainCheckpointTokenHolder(checkpoint, alg);
        var checkpointTimestamp = new DomainCheckpointTimestamp(
                tid,
                uid
        );
        var checkpointKey = createCheckpointKey(checkpointTimestamp, tokenHolder.getCheckpointToken());
        checkpointCacheRepository.cacheHashValue(
                tokenHolder.getCheckpointToken(),
                checkpointTimestamp
        );
        checkpointCacheRepository.cacheValue(checkpointKey, checkpoint);
        log.info("created new checkpoint: '{}'", checkpoint.getId());
        return tokenHolder;
    }

    @Override
    public String writeCheckpointCacheKey(String tid, long uid, String token) {
        var rawKey = String.format(
                CHECKPOINT_CACHE_KEY,
                tid,
                uid,
                token
        );
        return DigestUtils.sha256Hex(rawKey);
    }

    private String createCheckpointKey(CheckpointTimestamp checkpointTimestamp,
                                       String xCheckpointToken){
        return writeCheckpointCacheKey(
                checkpointTimestamp.getTenantId(),
                checkpointTimestamp.getUserId(),
                xCheckpointToken
        );
    }
}
