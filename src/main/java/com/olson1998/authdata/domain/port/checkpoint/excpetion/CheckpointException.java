package com.olson1998.authdata.domain.port.checkpoint.excpetion;

public abstract class CheckpointException extends IllegalStateException {

    public abstract String getHeaderValue();

    public abstract String getMessage();
}
