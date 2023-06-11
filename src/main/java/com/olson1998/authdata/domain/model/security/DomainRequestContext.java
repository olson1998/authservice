package com.olson1998.authdata.domain.model.security;

import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DomainRequestContext implements RequestContext {

    private UUID id;

    private String tenantId;

    private long userId;
}
