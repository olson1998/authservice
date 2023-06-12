package com.olson1998.authdata.domain.port.security.repository;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.olson1998.authdata.domain.port.caching.stereotype.CheckpointTimestamp;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;
import com.olson1998.authdata.domain.port.security.stereotype.CheckpointContext;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;

public interface RequestContextFactory {

    RequestContext fabricate(DecodedJWT jwt);

    CheckpointContext fabricate(String xCheckpointToken, CheckpointTimestamp checkpointTimestamp, Checkpoint checkpoint);
}
