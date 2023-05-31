package com.olson1998.authservice.application.requesting.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.domain.port.request.stereotype.data.RoleBindingClaim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleBindingForm implements RoleBindingClaim {

    @JsonProperty(value = "role_id")
    private String roleId;

    @JsonProperty(value = "authorityId")
    private String authorityId;

    public RoleBindingForm(String roleId) {
        this.roleId = roleId;
    }
}
