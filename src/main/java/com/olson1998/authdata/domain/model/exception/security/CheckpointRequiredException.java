package com.olson1998.authdata.domain.model.exception.security;

import com.olson1998.authdata.domain.port.security.exception.TokenVerificationException;

public class CheckpointRequiredException extends TokenVerificationException {

    @Override
    public int getStatusCode() {
        return 503;
    }

    @Override
    public String getDisplayName() {
        return "checkpoint required";
    }
}