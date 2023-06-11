package com.olson1998.authdata.domain.model.exception.checkpoint;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;

import java.util.Date;

public class CheckpointExpiredException extends CheckpointVerificationException {

    public static final String EXCEPTION_MESSAGE = "checkpoint expired";

    private final Long expireTime;

    public CheckpointExpiredException(Long expireTime) {
        super(EXCEPTION_MESSAGE, new SecurityException());
        this.expireTime = expireTime;
    }

    @Override
    public String getHeaderValue() {
        return EXCEPTION_MESSAGE;
    }

    @Override
    public String getMessage() {
        return "checkpoint expired at: " + new Date(expireTime);
    }
}
