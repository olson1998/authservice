package com.olson1998.authdata.domain.port.checkpoint.stereotype;

public interface CheckpointValues {

    String getTenantId();

    Long getExpireTime();

    Integer getMaxUsageCount();
}
