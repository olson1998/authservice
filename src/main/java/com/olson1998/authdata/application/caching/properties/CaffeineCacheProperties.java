package com.olson1998.authdata.application.caching.properties;

import java.time.Duration;

public interface CaffeineCacheProperties {

    Duration getValueCacheTimeoutDuration();

    Duration getHashValueCacheTimeoutDuration();
}
