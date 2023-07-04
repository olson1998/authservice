package com.olson1998.authdata.domain.model.exception.pipeline;

import com.olson1998.authdata.domain.port.processing.exception.PipelineRuntimeException;
import lombok.Getter;
import org.slf4j.Logger;

@Getter
public class PipelineJobException extends PipelineRuntimeException {

    private final Logger serviceLogger;

    private final String message;

    public PipelineJobException(Logger serviceLogger, Throwable e) {
        this.serviceLogger = serviceLogger;
        this.message = "unhandled exception caught: " + e.getMessage();
    }
}
