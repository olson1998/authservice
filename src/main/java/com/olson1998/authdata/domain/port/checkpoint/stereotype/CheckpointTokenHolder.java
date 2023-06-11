package com.olson1998.authdata.domain.port.checkpoint.stereotype;

public interface CheckpointTokenHolder extends CheckpointValues {

    String getCheckpointToken();

    String getTenantToken();

    String getUserToken();
}
