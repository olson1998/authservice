package com.olson1998.authdata.application.caching.repository;

import com.olson1998.authdata.application.caching.properties.CaffeineCacheProperties;
import com.olson1998.authdata.domain.port.caching.repository.CacheRepository;
import waffle.util.cache.CaffeineCache;

import java.util.Optional;

public abstract class CaffeineCacheRepository<HK, HV, K, V> implements CacheRepository<HK, HV, K, V> {

    private final CaffeineCache<K, V> valueCache;

    private final CaffeineCache<HK, HV> hashValueCache;

    @Override
    public Optional<V> getValue(K key) {
        return Optional.ofNullable(valueCache.get(key));
    }

    @Override
    public void cacheValue(K key, V value) {
        valueCache.put(key, value);
    }

    @Override
    public Optional<HV> getHashValue(HK hashKey) {
        return Optional.ofNullable(hashValueCache.get(hashKey));
    }

    @Override
    public void cacheHashValue(HK hashKey, HV hashValue) {
       hashValueCache.put(hashKey, hashValue);
    }

    protected CaffeineCacheRepository(CaffeineCacheProperties properties) {
        this.valueCache = new CaffeineCache<>(properties.getValueCacheTimeoutDuration().toMillis());
        this.hashValueCache = new CaffeineCache<>(properties.getHashValueCacheTimeoutDuration().toMillis());
    }
}
