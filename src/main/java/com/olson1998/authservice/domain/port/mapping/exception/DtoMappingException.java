package com.olson1998.authservice.domain.port.mapping.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DtoMappingException extends Exception {

    private final Class<?> entityClass;

    private final String reason;

    @Override
    public abstract String getMessage();
}