package com.olson1998.authdata.domain.model.checkpoint;

import com.olson1998.authdata.domain.port.caching.stereotype.CheckpointTimestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DomainCheckpointTimestamp implements CheckpointTimestamp {

    private final String tenantId;

    private final long userId;

}
