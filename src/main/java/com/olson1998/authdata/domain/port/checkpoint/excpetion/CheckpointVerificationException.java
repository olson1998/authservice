package com.olson1998.authdata.domain.port.checkpoint.excpetion;

import org.springframework.security.core.AuthenticationException;

public abstract class CheckpointVerificationException extends AuthenticationException {

    public abstract String getHeaderValue();

    public CheckpointVerificationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
