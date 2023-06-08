package com.olson1998.authdata.application.caching.properties.impl;

import com.olson1998.authdata.application.caching.properties.CaffeineCacheProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.MINUTES;

@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.service.support.caffeine.cache.checkpoint")
public class CheckpointCacheProperties implements CaffeineCacheProperties {

    private final KeyCacheTimeout key = new KeyCacheTimeout();

    private final HashKeyCacheTimeout hashKey = new HashKeyCacheTimeout();

    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    protected static class KeyCacheTimeout{

        private long timeout;

        private ChronoUnit timeoutUnit;
    }

    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    protected static class HashKeyCacheTimeout{

        private long timeout = 30L;

        private ChronoUnit timeoutUnit = MINUTES;
    }

    @Override
    public Duration getValueCacheTimeoutDuration() {
        return Duration.of(key.timeout, key.timeoutUnit);
    }

    @Override
    public Duration getHashValueCacheTimeoutDuration() {
        return Duration.of(hashKey.timeout, hashKey.timeoutUnit);
    }
}
