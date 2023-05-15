package com.olson1998.authservice.domain.model.exception.data;

import com.olson1998.authservice.domain.port.data.exception.RollbackRequiredException;

public class NoUserDeletedException extends RollbackRequiredException {

    @Override
    public String getMessage() {
        return "no user has been deleted, transaction rollback...";
    }
}
