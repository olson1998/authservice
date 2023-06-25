package com.olson1998.authdata.domain.model.exception.checkpoint;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import lombok.Getter;

import java.util.Date;

@Getter
public class CheckpointExpiredException extends CheckpointVerificationException {

    public static final String EXCEPTION_MESSAGE = "checkpoint expired";

    private final Long expireTime;

    private final int statusCode =403;

    private final String headerValue = "checkpoint expired";

    private final String message;

    public CheckpointExpiredException(Long expireTime) {
        super(EXCEPTION_MESSAGE, new IllegalStateException(EXCEPTION_MESSAGE));
        this.expireTime = expireTime;
        this.message = constructMessage(expireTime);
    }

    public String constructMessage(Long expireTime) {
        return "checkpoint expired at: " + new Date(expireTime);
    }
}
