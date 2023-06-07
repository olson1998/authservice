package com.olson1998.authdata.application.mapping.exception;

import com.olson1998.authdata.domain.port.processing.tree.exception.DtoMappingException;

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
