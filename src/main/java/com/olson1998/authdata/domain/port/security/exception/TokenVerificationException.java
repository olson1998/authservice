package com.olson1998.authdata.domain.port.security.exception;

import org.springframework.security.core.AuthenticationException;

public abstract class TokenVerificationException extends AuthenticationException {

    public TokenVerificationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TokenVerificationException(String msg) {
        super(msg);
    }

    public abstract int getStatusCode();

    public abstract String getDisplayName();

}
