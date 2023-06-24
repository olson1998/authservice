package com.olson1998.authdata.domain.model.exception.security;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import lombok.Getter;

@Getter
public class TenantSecretNotFound extends CheckpointVerificationException {

    private static final String TENANT_SECRET_NOT_FOUND = "tenant secret not found";

    private final int statusCode = 404;

    private final String headerValue = TENANT_SECRET_NOT_FOUND;

    public TenantSecretNotFound(Throwable cause) {
        super(TENANT_SECRET_NOT_FOUND, cause);
    }

}
