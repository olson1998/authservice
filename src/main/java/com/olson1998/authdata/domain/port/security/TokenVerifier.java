package com.olson1998.authdata.domain.port.security;

import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;

public interface TokenVerifier {

    RequestContext verifyJwt(String token);

    RequestContext verifyCheckpointToken(String xCheckpointToken);
}
