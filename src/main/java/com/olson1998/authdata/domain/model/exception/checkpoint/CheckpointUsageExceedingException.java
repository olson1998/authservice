package com.olson1998.authdata.domain.model.exception.checkpoint;

public class CheckpointUsageExceedingException extends SecurityException {

    private static final String EXCEPTION_MESSAGE = "checkpoint usage exceeded";

    private final Integer maxUsages;

    public CheckpointUsageExceedingException(Integer maxUsages) {
        this.maxUsages = maxUsages;
    }

}
