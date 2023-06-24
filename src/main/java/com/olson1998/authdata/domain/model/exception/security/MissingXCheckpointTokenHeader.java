package com.olson1998.authdata.domain.model.exception.security;

import com.olson1998.authdata.domain.port.checkpoint.excpetion.CheckpointVerificationException;
import lombok.Getter;

@Getter
public class MissingXCheckpointTokenHeader extends CheckpointVerificationException {

    private static final String MISSING_X_CHECKPOINT_HEADER = "missing x-checkpoint-token header";

    private final int statusCode = 200;

    private final String headerValue = MISSING_X_CHECKPOINT_HEADER;


    public MissingXCheckpointTokenHeader(Throwable cause) {
        super(MISSING_X_CHECKPOINT_HEADER, cause);
    }
}
