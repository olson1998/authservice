package com.olson1998.authdata.domain.port.caching.repository.impl;

import com.olson1998.authdata.domain.port.caching.repository.CacheRepository;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;

public interface TenantSecretCacheRepository extends CacheRepository<String, String, String, TenantSecret> {
}
