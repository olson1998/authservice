package com.olson1998.authdata.domain.port.security.exception;

import org.springframework.security.core.AuthenticationException;

public abstract class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
