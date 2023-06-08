package com.olson1998.authdata.domain.model.exception.checkpoint;

import com.olson1998.authdata.domain.port.processing.checkpoint.excpetion.CheckpointVerificationException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CheckpointTokenVerificationException extends CheckpointVerificationException {

    @Override
    public String getHeaderValue() {
        return "checkpoint verification exception";
    }

    @Override
    public String getMessage() {
        return "failed to verify given token";
    }
}
