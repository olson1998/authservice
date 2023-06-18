package com.olson1998.authdata.domain.port.caching.repository.impl;

import com.olson1998.authdata.domain.port.caching.repository.CacheRepository;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;

public interface TenantDataSourceCacheRepository extends CacheRepository<String, Long, String, TenantDataSource> {
}
