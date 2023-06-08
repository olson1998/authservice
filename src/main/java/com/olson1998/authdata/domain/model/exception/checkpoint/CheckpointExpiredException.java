package com.olson1998.authdata.domain.model.exception.checkpoint;

import com.olson1998.authdata.domain.port.processing.checkpoint.excpetion.CheckpointException;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class CheckpointExpiredException extends CheckpointException {

    private final Long expireTime;

    @Override
    public String getHeaderValue() {
        return "checkpoint expired";
    }

    @Override
    public String getMessage() {
        return "checkpoint expired at: " + String.valueOf(new Date(expireTime));
    }
}
