package com.olson1998.authdata.domain.model.exception.pipeline;

import com.olson1998.authdata.domain.port.processing.exception.PipelineRuntimeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor
public class DomainPipelineRuntimeException extends PipelineRuntimeException {

    @Getter
    private final Logger serviceLogger;

    private final Throwable error;

    @Override
    public String getMessage() {
        return error.getMessage();
    }
}
