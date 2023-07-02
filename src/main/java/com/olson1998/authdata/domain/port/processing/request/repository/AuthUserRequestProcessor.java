package com.olson1998.authdata.domain.port.processing.request.repository;

import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthUserObtainRequest;

public interface AuthUserRequestProcessor {

    AuthUser getAuthUser(AuthUserObtainRequest authUserObtainRequest);
}
