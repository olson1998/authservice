package com.olson1998.authservice.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CommonTestConst {

    public static final long NOW = System.currentTimeMillis();

    public static final long EXPIRE_IN_24H = NOW + Duration.ofHours(24).toMillis();

    public static final long EXPIRE_IN_12_H = NOW + Duration.ofHours(12).toMillis();
}
