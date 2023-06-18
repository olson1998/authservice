package com.olson1998.authdata.domain.model.exception.data;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.util.UUID;

@RequiredArgsConstructor
public class DifferentUpdatedEntitiesTimestampsException extends RollbackRequiredException {

    @Getter
    private final Logger serviceLogger;

    @Getter
    private final UUID requestId;

    private final int expectedRows;

    private final int actualRows;

    @Override
    public String getMessage() {
        return "Expected to update %s timestamps but actual updated was %s".formatted(expectedRows, actualRows);
    }
}
