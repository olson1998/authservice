package com.olson1998.authdata.domain.model.processing.request;

import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.Getter;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class LinkedAuthoritySavingRequest implements AuthoritySavingRequest {

    private final UUID id;

    private final Set<AuthorityDetails> authoritiesDetails;

    public LinkedAuthoritySavingRequest(@NonNull RoleBoundSavingRequest roleBoundSavingRequest) {
        this.id = roleBoundSavingRequest.getId();
        this.authoritiesDetails = new HashSet<>();
        roleBoundSavingRequest.getRoleIdAuthoritySavingRequestMap()
                .values()
                .forEach(this.authoritiesDetails::addAll);
    }
}
