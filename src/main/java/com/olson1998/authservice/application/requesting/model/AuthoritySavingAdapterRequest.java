package com.olson1998.authservice.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.requesting.model.payload.AuthorityDetailsForm;
import com.olson1998.authservice.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.olson1998.authservice.application.requesting.model.AbstractCommonJsonValues.ID;

@Getter
public class AuthoritySavingAdapterRequest implements AuthoritySavingRequest {

    private final UUID id;

    private final Set<AuthorityDetails> authoritiesDetails;

    @JsonCreator
    public AuthoritySavingAdapterRequest(@JsonProperty(value = ID, required = true) UUID id,
                                         @JsonProperty(value = "authority_details", required = true) Set<AuthorityDetailsForm> authorityDetails) {
        this.id = id;
        this.authoritiesDetails = authorityDetails.stream()
                .map(this::mapAuthorityDetails)
                .collect(Collectors.toUnmodifiableSet());
    }

    private AuthorityDetails mapAuthorityDetails(AuthorityDetailsForm authorityDetailsForm){
        return authorityDetailsForm;
    }
}
