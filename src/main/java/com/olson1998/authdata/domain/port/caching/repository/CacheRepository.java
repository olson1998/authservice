package com.olson1998.authdata.domain.port.caching.repository;

import java.util.Optional;

public interface CacheRepository<HK, HV, K, V> {

    Optional<V> getValue(K key);

    void cacheValue(K key, V value);

    Optional<HV> getHashValue(HK hashKey);

    void cacheHashValue(HK hashKey, HV hashValue);
}
