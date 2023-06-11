package com.olson1998.authdata.domain.model.exception.security;

import com.olson1998.authdata.domain.port.processing.checkpoint.excpetion.CheckpointException;
import com.olson1998.authdata.domain.port.processing.checkpoint.excpetion.CheckpointVerificationException;
import com.olson1998.authdata.domain.port.security.exception.TokenVerificationException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CheckpointTokenVerificationException extends TokenVerificationException {

    @Override
    public String getMessage() {
        return "failed to verify given token";
    }

    @Override
    public int getStatusCode() {
        return 401;
    }

    @Override
    public String getDisplayName() {
        return "checkpoint verification failed";
    }
}
