package com.olson1998.authdata.domain.port.caching.repository.impl;

import com.olson1998.authdata.domain.port.caching.repository.CacheRepository;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;

public interface CheckpointCacheRepository extends CacheRepository<String, String, String, Checkpoint> {
}
