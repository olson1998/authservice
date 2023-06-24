package com.olson1998.authdata.domain.model.exception.security;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import lombok.Getter;

@Getter
public class CheckpointTimestampNotFound extends CheckpointVerificationException {

    private static final String CHECKPOINT_TIMESTAMP_NOT_FOUND = "checkpoint timestamp not found";

    private final int statusCode = 404;

    private final String headerValue = CHECKPOINT_TIMESTAMP_NOT_FOUND;

    public CheckpointTimestampNotFound(Throwable cause) {
        super(CHECKPOINT_TIMESTAMP_NOT_FOUND, cause);
    }
}
