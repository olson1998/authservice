package com.olson1998.authdata.application.caching.repository.impl;

import com.olson1998.authdata.application.caching.properties.CaffeineCacheProperties;
import com.olson1998.authdata.application.caching.repository.CaffeineCacheRepository;
import com.olson1998.authdata.domain.port.caching.repository.impl.TenantSecretCacheRepository;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import org.springframework.stereotype.Component;

@Component
public class CaffeineTenantSecretCacheRepository extends CaffeineCacheRepository<String, String, String, TenantSecret> implements TenantSecretCacheRepository {

    protected CaffeineTenantSecretCacheRepository(CaffeineCacheProperties properties) {
        super(properties);
    }
}
