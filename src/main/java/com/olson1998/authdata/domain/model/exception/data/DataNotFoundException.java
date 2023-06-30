package com.olson1998.authdata.domain.model.exception.data;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import lombok.Getter;
import org.slf4j.Logger;

import java.util.UUID;

@Getter
public class DataNotFoundException extends RollbackRequiredException {

    private final Logger serviceLogger;

    private final UUID requestId;

    private final String message;

    public DataNotFoundException(Logger serviceLogger, UUID requestId, String message, Class<?> entityClass) {
        this.serviceLogger = serviceLogger;
        this.requestId = requestId;
        this.message = constructMessage(entityClass);
    }

    public String constructMessage(Class<?> entityClass) {
        return String.format("data not found for entity: '%s'", entityClass.getName());
    }
}
