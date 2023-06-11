package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.application.requesting.model.payload.AuthorityDetailsForm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AuthoritySavingAdapterRequest extends AbstractAdapterRequest implements AuthoritySavingRequest {

    private final Set<AuthorityDetails> authoritiesDetails;

    public AuthoritySavingAdapterRequest(Set<AuthorityDetailsForm> authorityDetails) {
        this.authoritiesDetails = authorityDetails.stream()
                .map(this::mapAuthorityDetails)
                .collect(Collectors.toUnmodifiableSet());
    }

    private AuthorityDetails mapAuthorityDetails(AuthorityDetailsForm authorityDetailsForm){
        return authorityDetailsForm;
    }

}
