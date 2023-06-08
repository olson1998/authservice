package com.olson1998.authdata.domain.port.processing.exception;

import org.slf4j.Logger;

public abstract class PipelineRuntimeException extends IllegalStateException {

    public abstract Logger getServiceLogger();

    @Override
    public abstract String getMessage();
}
