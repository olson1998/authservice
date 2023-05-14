package com.olson1998.authservice.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.datasource.entity.utils.SecretDigest;
import com.olson1998.authservice.application.requesting.entity.UserDetailsForm;
import com.olson1998.authservice.application.requesting.entity.UserMembershipForm;
import com.olson1998.authservice.domain.port.request.entity.UserDetails;
import com.olson1998.authservice.domain.port.request.entity.UserMembershipClaim;
import com.olson1998.authservice.domain.port.request.model.UserSavingRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.olson1998.authservice.application.requesting.model.AbstractCommonJsonValues.*;

@Getter

@RequiredArgsConstructor
public class ApplicationUserSavingRequest implements UserSavingRequest {

    private final UUID id;

    private final UserDetails userDetails;

    private final Set<UserMembershipClaim> membershipClaims;

    @JsonCreator
    public ApplicationUserSavingRequest(@JsonProperty(value = ID, required = true) UUID id,
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
        var membershipClaimsRef = new AtomicReference<Set<UserMembershipClaim>>();
        Optional.ofNullable(membershipClaimsForm).ifPresent(forms -> membershipClaimsRef.set(mapUserMembershipClaims(forms)));
        this.membershipClaims = membershipClaimsRef.get();
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
