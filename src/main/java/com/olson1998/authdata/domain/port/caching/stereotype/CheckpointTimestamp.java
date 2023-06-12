package com.olson1998.authdata.domain.port.caching.stereotype;

public interface CheckpointTimestamp {

    String getTenantId();

    long getUserId();

}
