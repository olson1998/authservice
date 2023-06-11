package com.olson1998.authdata.domain.model.security;

import com.olson1998.authdata.domain.port.security.stereotype.CheckpointContext;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DomainCheckpointRequestContext implements CheckpointContext {

    private UUID id;

    private String token;

    private String tenantId;

    private long userId;
}
