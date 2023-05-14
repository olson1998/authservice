package com.olson1998.authservice.domain.model.exception.data;

import com.olson1998.authservice.domain.port.data.exception.NoBindingEntityRowsDeletedException;

public class NoUserDeletedException extends NoBindingEntityRowsDeletedException {

    @Override
    public String getMessage() {
        return "no user has been deleted, transaction rollback...";
    }
}
