package com.olson1998.authdata.domain.port.security.exception;

public abstract class TokenVerificationException extends SecurityException {

    public abstract int getStatusCode();

    public abstract String getDisplayName();
}
