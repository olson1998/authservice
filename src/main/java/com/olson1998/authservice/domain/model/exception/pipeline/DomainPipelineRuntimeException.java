package com.olson1998.authservice.domain.model.exception.pipeline;

import com.olson1998.authservice.domain.port.processing.exception.PipelineRuntimeException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomainPipelineRuntimeException extends PipelineRuntimeException {

    private final Throwable error;

    @Override
    public String getMessage() {
        return error.getMessage();
    }
}
