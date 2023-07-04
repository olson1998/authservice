package com.olson1998.authdata.domain.model.exception.data;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DeletedRolesQtyDoesntMuchRequestedQty extends RollbackRequiredException {

    private final int expectedQty;

    private final int actualQty;

    private final UUID requestId;

    private final Logger serviceLogger;

    @Override
    public String getMessage() {
        return String.format("expected to delete %s but actual number was %s", expectedQty, actualQty);
    }
}
