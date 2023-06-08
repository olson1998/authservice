package com.olson1998.authdata.application.caching.repository.impl;

import com.olson1998.authdata.application.caching.properties.impl.CheckpointCacheProperties;
import com.olson1998.authdata.application.caching.repository.CaffeineCacheRepository;
import com.olson1998.authdata.domain.port.processing.checkpoint.stereotype.Checkpoint;
import org.springframework.stereotype.Service;

@Service
public class CheckpointCacheRepository extends CaffeineCacheRepository<String, String, String, Checkpoint> {

    protected CheckpointCacheRepository(CheckpointCacheProperties properties) {
        super(properties);
    }
}
