package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.application.requesting.AdapterRequestContextHolder;

public abstract class AbstractUserAdapterRequest extends AbstractAdapterRequest{

    public long getUserId() {
        return AdapterRequestContextHolder.getLocalThreadUserId();
    }
}
