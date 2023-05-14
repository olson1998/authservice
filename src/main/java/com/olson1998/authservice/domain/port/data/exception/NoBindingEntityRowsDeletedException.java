package com.olson1998.authservice.domain.port.data.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class NoBindingEntityRowsDeletedException extends Exception {

    @Override
    public abstract String getMessage();
}
