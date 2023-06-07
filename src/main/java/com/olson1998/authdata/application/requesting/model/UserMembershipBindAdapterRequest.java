package com.olson1998.authdata.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.application.requesting.model.payload.UserMembershipForm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipBindRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.ID;
import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.USER_ID;

@Getter
public class UserMembershipBindAdapterRequest implements UserMembershipBindRequest {

    private final UUID id;

    private final Long userId;

    private final Set<UserMembershipClaim> userMembershipClaims;

    @JsonCreator
    public UserMembershipBindAdapterRequest(@JsonProperty(value = ID, required = true) UUID id,
                                            @JsonProperty(value = USER_ID, required = true) Long userId,
                                            @JsonProperty(value = "claims", required = true)Set<UserMembershipForm> userMembershipForms) {
        this.id = id;
        this.userId = userId;
        this.userMembershipClaims = userMembershipForms.stream()
                .map(UserMembershipClaim.class::cast)
                .collect(Collectors.toUnmodifiableSet());
    }
}
