package com.olson1998.authservice.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.requesting.model.payload.AuthorityDetailsForm;
import com.olson1998.authservice.application.requesting.model.payload.RoleBindingForm;
import com.olson1998.authservice.domain.port.processing.request.stereotype.RoleBindingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.olson1998.authservice.application.requesting.model.AbstractCommonJsonValues.ID;

@Getter
public class RoleBindingAdapterRequest implements RoleBindingRequest {

    private final UUID id;

    private final Set<RoleBindingClaim> rolesBindingsClaims;

    private final Map<String, Set<AuthorityDetails>> roleIdAuthoritySavingRequestMap;

    @JsonCreator
    public RoleBindingAdapterRequest(@JsonProperty(value = ID, required = true) UUID id,
                                     @JsonProperty(value = "role_bindings", required = true)Set<RoleBindingForm> roleBindingFormSet,
                                     @JsonProperty(value = "bind_saved_authorities") Map<String, Set<AuthorityDetailsForm>> claimedToSaveAuthoritiesDetails) {
        this.id = id;
        this.rolesBindingsClaims = roleBindingFormSet.stream()
                .map(this::mapRoleBindingClaim)
                .collect(Collectors.toUnmodifiableSet());
        this.roleIdAuthoritySavingRequestMap = new HashMap<>();
        if(claimedToSaveAuthoritiesDetails != null){
            claimedToSaveAuthoritiesDetails.forEach((roleId, authoritiesDetails) ->{
                var mappedAuthorities = authoritiesDetails.stream()
                        .map(this::mapAuthorityDetails)
                        .collect(Collectors.toUnmodifiableSet());
                this.roleIdAuthoritySavingRequestMap.put(roleId, mappedAuthorities);
            });
        }
    }

    private AuthorityDetails mapAuthorityDetails(AuthorityDetailsForm authorityDetailsForm){
        return authorityDetailsForm;
    }

    private RoleBindingClaim mapRoleBindingClaim(RoleBindingForm form){
        return form;
    }
}
