package com.olson1998.authdata.domain.model.exception.security;

import com.olson1998.authdata.domain.port.security.exception.TokenVerificationException;

public class TenantSecretNotFound extends TokenVerificationException {

    @Override
    public int getStatusCode() {
        return 403;
    }

    @Override
    public String getDisplayName() {
        return "tenant does not exist";
    }
}
