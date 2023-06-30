package com.olson1998.authdata.domain.model.exception.data;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import lombok.Getter;
import org.slf4j.Logger;

import java.util.UUID;

@Getter
public class DifferentAffectedRowsThanRequired extends RollbackRequiredException {

    private final Logger serviceLogger;

    private final UUID requestId;

    private final String message;

    public DifferentAffectedRowsThanRequired(Logger serviceLogger, UUID requestId, int expectedRows, int actualRows) {
        this.serviceLogger = serviceLogger;
        this.requestId = requestId;
        this.message = constructMessage(expectedRows, actualRows);
    }

    public String constructMessage(int expectedRows, int actualRows) {
        return "Expected to update %s timestamps but actual updated was %s".formatted(expectedRows, actualRows);
    }
}
