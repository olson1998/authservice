package com.olson1998.authdata.domain.port.checkpoint.excpetion;

import org.springframework.security.core.AuthenticationException;

public abstract class CheckpointVerificationException extends AuthenticationException {

    public abstract int getStatusCode();

    public abstract String getHeaderValue();

    @Override
    public abstract String getMessage();

    public CheckpointVerificationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
