package com.olson1998.authdata.domain.model.exception.security;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import lombok.Getter;

@Getter
public class CheckpointNotFoundException extends CheckpointVerificationException {

    private static final String CHECKPOINT_NOT_FOUND = "checkpoint not found";

    private final int statusCode = 401;

    private final String headerValue = CHECKPOINT_NOT_FOUND;

    public CheckpointNotFoundException(Throwable cause) {
        super(CHECKPOINT_NOT_FOUND, cause);
    }
}
