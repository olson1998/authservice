package com.olson1998.authservice.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.application.datasource.entity.utils.RoleSubject;
import com.olson1998.authservice.application.requesting.entity.RoleDetailsForm;
import com.olson1998.authservice.domain.port.request.entity.RoleDetails;
import com.olson1998.authservice.domain.port.request.model.RoleSavingRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.olson1998.authservice.application.requesting.model.AbstractCommonJsonValues.*;

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
