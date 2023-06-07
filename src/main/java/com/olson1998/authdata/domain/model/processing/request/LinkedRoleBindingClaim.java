package com.olson1998.authdata.domain.model.processing.request;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LinkedRoleBindingClaim implements RoleBindingClaim {

    private final String roleId;

    private final String authorityId;
}
