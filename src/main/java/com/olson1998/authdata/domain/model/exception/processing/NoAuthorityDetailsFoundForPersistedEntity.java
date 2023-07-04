package com.olson1998.authdata.domain.model.exception.processing;

import com.olson1998.authdata.domain.port.processing.exception.PipelineRuntimeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor
public class NoAuthorityDetailsFoundForPersistedEntity extends PipelineRuntimeException {

    @Getter
    private final Logger serviceLogger;

    @Override
    public String getMessage() {
        return "No authority persisted for entity";
    }
}
