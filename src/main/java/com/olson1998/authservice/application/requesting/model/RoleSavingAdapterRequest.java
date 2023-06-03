package com.olson1998.authservice.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.requesting.model.payload.RoleDetailsForm;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleDetails;
import com.olson1998.authservice.domain.port.processing.request.stereotype.RoleSavingRequest;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.olson1998.authservice.application.requesting.model.AbstractCommonJsonValues.ID;

@Getter
public class RoleSavingAdapterRequest implements RoleSavingRequest {

    private final UUID id;

    private final Set<RoleDetails> details;

    @JsonCreator
    public RoleSavingAdapterRequest(@JsonProperty(value = ID, required = true) UUID id,
                                    @JsonProperty(value = "role_details", required = true) Set<RoleDetailsForm> roleDetailsForms) {
        this.id = id;
        this.details = mapRoleDetailsForms(roleDetailsForms);
    }

    private Set<RoleDetails> mapRoleDetailsForms(Set<RoleDetailsForm> roleDetailsForms){
        return roleDetailsForms.stream()
                .map(this::mapRoleDetailsForm)
                .collect(Collectors.toUnmodifiableSet());
    }

    private RoleDetails mapRoleDetailsForm(RoleDetailsForm form){
        return form;
    }
}
