package com.olson1998.authdata.domain.model.exception.data;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class NoUserDeletedException extends RollbackRequiredException {

    @Getter
    private final Logger serviceLogger;

    private final UUID requestId;

    @Override
    public String getMessage() {
        return "no user has been deleted, transaction rollback...";
    }
}
