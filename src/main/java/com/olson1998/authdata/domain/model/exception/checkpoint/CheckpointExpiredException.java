package com.olson1998.authdata.domain.model.exception.checkpoint;

import java.util.Date;

public class CheckpointExpiredException extends SecurityException {

    public static final String EXCEPTION_MESSAGE = "checkpoint expired";

    private final Long expireTime;

    public CheckpointExpiredException(Long expireTime) {
        super(EXCEPTION_MESSAGE, new SecurityException());
        this.expireTime = expireTime;
    }

    @Override
    public String getMessage() {
        return "checkpoint expired at: " + new Date(expireTime);
    }
}
