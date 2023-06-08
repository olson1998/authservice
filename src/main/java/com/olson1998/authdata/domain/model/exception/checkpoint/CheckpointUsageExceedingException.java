package com.olson1998.authdata.domain.model.exception.checkpoint;

import com.olson1998.authdata.domain.port.processing.checkpoint.excpetion.CheckpointException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CheckpointUsageExceedingException extends CheckpointException {

    private final Integer maxUsages;

    @Override
    public String getHeaderValue() {
        return "checkpoint usage exceeded";
    }

    @Override
    public String getMessage() {
        return "exceeded checkpoint usage, max: " + maxUsages;
    }
}
