package com.olson1998.authdata.application.caching.repository.impl;

import com.olson1998.authdata.application.caching.properties.CaffeineCacheProperties;
import com.olson1998.authdata.application.caching.repository.CaffeineCacheRepository;
import com.olson1998.authdata.domain.port.caching.repository.impl.TenantDataSourceCacheRepository;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;
import org.springframework.stereotype.Component;

@Component
public class TenantDataSourceCaffeineCache extends CaffeineCacheRepository<String, Long, String, TenantDataSource> implements TenantDataSourceCacheRepository {

    protected TenantDataSourceCaffeineCache(CaffeineCacheProperties properties) {
        super(properties);
    }
}
