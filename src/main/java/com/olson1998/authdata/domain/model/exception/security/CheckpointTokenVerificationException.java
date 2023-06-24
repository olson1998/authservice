package com.olson1998.authdata.domain.model.exception.security;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import lombok.Getter;

@Getter
public class CheckpointTokenVerificationException extends CheckpointVerificationException {

    private static final String CHECKPOINT_VERIFICATION_FAILED = "checkpoint verification failed";

    private final int statusCode = 403;

    private final String headerValue = CHECKPOINT_VERIFICATION_FAILED;

    public CheckpointTokenVerificationException(Throwable cause) {
        super(CHECKPOINT_VERIFICATION_FAILED, cause);
    }
}
