package com.olson1998.authservice.domain.port.processing.exception;

public abstract class PipelineRuntimeException extends IllegalStateException {

    @Override
    public abstract String getMessage();
}
