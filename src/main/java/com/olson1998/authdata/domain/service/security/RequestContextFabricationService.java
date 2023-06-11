package com.olson1998.authdata.domain.service.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.olson1998.authdata.domain.model.security.DomainRequestContext;
import com.olson1998.authdata.domain.port.processing.checkpoint.stereotype.Checkpoint;
import com.olson1998.authdata.domain.port.security.RequestContextFactory;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.UUID;

@NoArgsConstructor
public class RequestContextFabricationService implements RequestContextFactory {

    protected static final String TENANT_ID = "tid";

    private static final String USER_ID = "uid";

    @Override
    public RequestContext fabricate(@NonNull DecodedJWT jwt) {
        return DomainRequestContext.builder()
                .id(UUID.fromString(jwt.getId()))
                .tenantId(jwt.getClaim(TENANT_ID).asString())
                .userId(jwt.getClaim(USER_ID).asLong())
                .build();
    }

    @Override
    public RequestContext fabricate(@NonNull Checkpoint checkpoint) {
        return DomainRequestContext.builder()
                .tenantId(checkpoint.getTenantId())
                .userId(checkpoint.getUserId())
                .id(checkpoint.getId())
                .build();
    }
}
