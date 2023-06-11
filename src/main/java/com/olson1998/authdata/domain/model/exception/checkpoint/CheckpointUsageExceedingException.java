package com.olson1998.authdata.domain.model.exception.checkpoint;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;

public class CheckpointUsageExceedingException extends CheckpointVerificationException {

    private static final String EXCEPTION_MESSAGE = "checkpoint usage exceeded";

    private final Integer maxUsages;

    public CheckpointUsageExceedingException(Integer maxUsages) {
        super(EXCEPTION_MESSAGE, new SecurityException());
        this.maxUsages = maxUsages;
    }

    @Override
    public String getHeaderValue() {
        return "checkpoint usage exceeded, max: " + maxUsages;
    }

}
