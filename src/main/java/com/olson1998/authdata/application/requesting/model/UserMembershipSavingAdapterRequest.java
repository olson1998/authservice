package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.application.requesting.model.payload.UserMembershipForm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class UserMembershipSavingAdapterRequest extends AbstractUserAdapterRequest implements UserMembershipSavingRequest {

    private final Set<UserMembershipClaim> userMembershipClaims;

    public UserMembershipSavingAdapterRequest(Set<UserMembershipForm> userMembershipForms) {
        this.userMembershipClaims = userMembershipForms.stream()
                .map(UserMembershipClaim.class::cast)
                .collect(Collectors.toUnmodifiableSet());
    }

}
