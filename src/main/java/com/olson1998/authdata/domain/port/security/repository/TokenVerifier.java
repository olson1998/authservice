package com.olson1998.authdata.domain.port.security.repository;

import com.olson1998.authdata.domain.port.security.stereotype.CheckpointContext;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;

public interface TokenVerifier {

    RequestContext verifyJwt(String token);

    CheckpointContext verifyCheckpointToken(String xCheckpointToken);
}
