package com.olson1998.authdata.application.processing.pipeline.exception;

import com.olson1998.authdata.domain.port.processing.exception.PipelineRuntimeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor
public class PipelineFabricationException extends PipelineRuntimeException {

    @Getter
    private final Logger serviceLogger;

    @Override
    public String getMessage() {
        return "could not fabricate pipeline due to null request";
    }
}
