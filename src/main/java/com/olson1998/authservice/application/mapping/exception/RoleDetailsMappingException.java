package com.olson1998.authservice.application.mapping.exception;

import com.olson1998.authservice.domain.port.mapping.exception.DtoMappingException;

public class RoleDetailsMappingException extends DtoMappingException {

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }

    public RoleDetailsMappingException(Class<?> entityClass, String reason) {
        super(entityClass, reason);
        this.message = String.format(
                "failed to map %s, reason: %s",
                entityClass.getName(),
                reason
        );
    }
}