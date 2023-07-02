package com.olson1998.authdata.domain.port.security.repository;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;

public interface RequestContextFactory {

    RequestContext fabricate(DecodedJWT jwt);

}
