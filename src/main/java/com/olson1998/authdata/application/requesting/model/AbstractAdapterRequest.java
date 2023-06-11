package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.application.requesting.AdapterRequestContextHolder;
import com.olson1998.authdata.domain.port.processing.request.stereotype.Request;

import java.util.UUID;

public abstract class AbstractAdapterRequest implements Request {

    @Override
    public UUID getId() {
        return AdapterRequestContextHolder.getLocalThreadRequestId();
    }
}
