package com.olson1998.authdata.domain.model.exception.checkpoint;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import lombok.Getter;

@Getter
public class CheckpointUsageExceedingException extends CheckpointVerificationException {

    private static final String EXCEPTION_MESSAGE = "checkpoint usage exceeded";

    private final Integer maxUsage;

    private final int statusCode = 403;

    private final String headerValue = EXCEPTION_MESSAGE;

    private final String message;

    public CheckpointUsageExceedingException(Integer maxUsage) {
        super(EXCEPTION_MESSAGE, new IllegalStateException(EXCEPTION_MESSAGE));
        this.maxUsage = maxUsage;
        this.message = constructMessage(maxUsage);
    }

    private String constructMessage(Integer maxUsage){
        return new StringBuilder(EXCEPTION_MESSAGE)
                .append(" max usage count is: ")
                .append(maxUsage)
                .toString();
    }
}
