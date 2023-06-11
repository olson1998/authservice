package com.olson1998.authdata.domain.port.checkpoint.stereotype;

public interface CheckpointValues {

    String getTenantId();

    long getTimestamp();

    Long getExpireTime();

    Integer getMaxUsageCount();
}
