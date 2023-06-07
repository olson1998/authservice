package com.olson1998.authdata.application.requesting.model.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleBindingForm implements RoleBindingClaim {

    @JsonProperty(value = "role_id")
    private String roleId;

    @JsonProperty(value = "authority_d")
    private String authorityId;

    public RoleBindingForm(String roleId) {
        this.roleId = roleId;
    }
}
