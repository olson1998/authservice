package com.olson1998.authdata.domain.port.data.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class RollbackRequiredException extends IllegalStateException {

    public abstract Logger getServiceLogger();

    public abstract UUID getRequestId();

    @Override
    public abstract String getMessage();
}
