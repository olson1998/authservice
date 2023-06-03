package com.olson1998.authservice.domain.port.processing.request.exception;

import java.util.UUID;

public abstract class DataPolicyViolationException extends IllegalArgumentException {

    public abstract UUID getRequestId();

    public abstract int getViolatedParagraph();

    public abstract String getViolatedPolicy();

    @Override
    public abstract String getMessage();
}
