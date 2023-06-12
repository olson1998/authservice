package com.olson1998.authdata.application.caching.repository.impl;

import com.olson1998.authdata.application.caching.properties.impl.CheckpointCacheProperties;
import com.olson1998.authdata.application.caching.repository.CaffeineCacheRepository;
import com.olson1998.authdata.domain.port.caching.repository.impl.CheckpointCacheRepository;
import com.olson1998.authdata.domain.port.caching.stereotype.CheckpointTimestamp;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;
import org.springframework.stereotype.Component;

@Component
public class CaffeineCheckpointCacheRepository extends CaffeineCacheRepository<String, CheckpointTimestamp, String, Checkpoint> implements CheckpointCacheRepository {

    public CaffeineCheckpointCacheRepository(CheckpointCacheProperties properties) {
        super(properties);
    }
}
