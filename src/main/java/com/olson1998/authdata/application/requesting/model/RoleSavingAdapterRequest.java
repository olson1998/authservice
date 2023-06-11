package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.application.requesting.model.payload.RoleDetailsForm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class RoleSavingAdapterRequest extends AbstractAdapterRequest implements RoleSavingRequest {

    private final Set<RoleDetails> details;

    public RoleSavingAdapterRequest(Set<RoleDetailsForm> roleDetailsForms) {
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
