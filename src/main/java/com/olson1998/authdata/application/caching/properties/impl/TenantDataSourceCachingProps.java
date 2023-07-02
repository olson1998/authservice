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

@Setter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.authdata.application.caffeine.caching.properties.tenant-data-source")
public class TenantDataSourceCachingProps implements CaffeineCacheProperties {

    private ValueCache value = new ValueCache();

    private HashCache hash = new HashCache();

    @Setter(value = AccessLevel.PUBLIC)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HashCache{

        private long duration = 30;

        private ChronoUnit unit = ChronoUnit.MINUTES;
    }

    @Setter(value = AccessLevel.PUBLIC)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class ValueCache{

        private long duration = 30;

        private ChronoUnit unit = ChronoUnit.MINUTES;

    }

    @Override
    public Duration getValueCacheTimeoutDuration() {
        return Duration.of(
                value.duration,
                value.unit
        );
    }

    @Override
    public Duration getHashValueCacheTimeoutDuration() {
        return Duration.of(
                hash.duration,
                hash.unit
        );
    }
}
