package com.olson1998.authservice.domain.model.processing.request;

import com.olson1998.authservice.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.RoleBindingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class LinkedAuthoritySavingRequest implements AuthoritySavingRequest {

    private final UUID id;

    private final Set<AuthorityDetails> authoritiesDetails;

    public LinkedAuthoritySavingRequest(@NonNull RoleBindingRequest roleBindingRequest) {
        this.id = roleBindingRequest.getId();
        this.authoritiesDetails = new HashSet<>();
        roleBindingRequest.getRoleIdAuthoritySavingRequestMap()
                .values()
                .forEach(this.authoritiesDetails::addAll);
    }
}
