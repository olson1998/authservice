package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.application.requesting.AdapterRequestContextHolder;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthUserObtainRequest;

public class AuthUserObtainAdapterRequest extends AbstractAdapterRequest implements AuthUserObtainRequest {

    @Override
    public long getUserId() {
        return AdapterRequestContextHolder.getLocalThreadUserId();
    }
}
