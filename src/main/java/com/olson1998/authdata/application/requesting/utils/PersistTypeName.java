package com.olson1998.authdata.application.requesting.utils;

import com.olson1998.authdata.domain.port.processing.request.utils.PersistType;

public enum PersistTypeName implements PersistType {

    SAVE,
    BIND;

    @Override
    public boolean isSave() {
        return this.equals(SAVE);
    }

    @Override
    public boolean isBind() {
        return this.equals(BIND);
    }
}
