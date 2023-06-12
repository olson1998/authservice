package com.olson1998.authdata.domain.port.security.repository;

import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.domain.port.caching.stereotype.CheckpointTimestamp;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;

import java.util.Optional;

public interface CheckpointProvider {

    Optional<CheckpointTimestamp> getCheckpointTimestamp(String xCheckpointToken);

    Optional<Checkpoint> getCheckpoint(String xCheckpointToken, CheckpointTimestamp checkpointTimestamp, Algorithm algorithm);

}
