package com.olson1998.authdata.domain.service.security;

import com.olson1998.authdata.domain.port.caching.repository.impl.CheckpointCacheRepository;
import com.olson1998.authdata.domain.port.caching.stereotype.CheckpointTimestamp;
import com.olson1998.authdata.domain.port.checkpoint.repository.CheckpointRepository;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;
import com.olson1998.authdata.domain.port.security.repository.CheckpointProvider;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CheckpointProvidingService implements CheckpointProvider {

    private final CheckpointRepository checkpointRepository;

    private final CheckpointCacheRepository checkpointCacheRepository;

    @Override
    public Optional<CheckpointTimestamp> getCheckpointTimestamp(String xCheckpointToken) {
        return checkpointCacheRepository.getHashValue(xCheckpointToken);
    }

    @Override
    public Optional<Checkpoint> getCheckpoint(String xCheckpointToken, CheckpointTimestamp checkpointTimestamp) {
        var tid = checkpointTimestamp.getTenantId();
        var key = checkpointRepository.writeCheckpointCacheKey(
                tid,
                checkpointTimestamp.getUserId(),
                xCheckpointToken
        );
        return checkpointCacheRepository.getValue(key);
    }

}
