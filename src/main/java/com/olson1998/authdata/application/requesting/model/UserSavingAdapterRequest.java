package com.olson1998.authdata.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.application.datasource.entity.utils.SecretDigest;
import com.olson1998.authdata.application.requesting.model.payload.UserDetailsForm;
import com.olson1998.authdata.application.requesting.model.payload.UserMembershipForm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.ID;

@Getter

@RequiredArgsConstructor
public class UserSavingAdapterRequest implements UserSavingRequest {

    private final UUID id;

    private final UserDetails userDetails;

    private final Set<UserMembershipClaim> membershipClaims;

    @JsonCreator
    public UserSavingAdapterRequest(@JsonProperty(value = ID, required = true) UUID id,
                                    @JsonProperty(value = "username", required = true) String username,
                                    @JsonProperty(value = "password", required = true) String password,
                                    @JsonProperty(value = "digest", required = true) SecretDigest passwordDigest,
                                    @JsonProperty(value = "membership") Set<UserMembershipForm> membershipClaimsForm) {
        this.id=id;
        this.userDetails = new UserDetailsForm(
                username,
                password,
                passwordDigest
        );
        if(membershipClaimsForm != null){
            this.membershipClaims = mapUserMembershipClaims(membershipClaimsForm);
        }else {
            this.membershipClaims = null;
        }
    }

    private Set<UserMembershipClaim> mapUserMembershipClaims(Set<UserMembershipForm> membershipClaimsForm){
        return membershipClaimsForm.stream()
                .map(this::mapClaim)
                .collect(Collectors.toUnmodifiableSet());
    }

    private UserMembershipClaim mapClaim(UserMembershipForm form){
        return form;
    }

}