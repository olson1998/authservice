package com.olson1998.authdata.domain.model.exception.processing;

import com.olson1998.authdata.domain.port.processing.exception.PipelineRuntimeException;

public class MismatchAuthoritiesTreeUserIdException extends PipelineRuntimeException {

    @Override
    public String getMessage() {
        return "cannot compare authorities trees with different user's id";
    }
}
