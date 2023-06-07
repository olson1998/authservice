package com.olson1998.authdata.domain.model.exception.pipeline;

import com.olson1998.authdata.domain.port.processing.exception.PipelineRuntimeException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomainPipelineRuntimeException extends PipelineRuntimeException {

    private final Throwable error;

    @Override
    public String getMessage() {
        return error.getMessage();
    }
}
