package com.olson1998.authservice.domain.model.processing.request;

import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LinkedRoleBindingClaim implements RoleBindingClaim {

    private final String roleId;

    private final String authorityId;
}
