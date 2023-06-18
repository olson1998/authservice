package com.olson1998.authdata.domain.model.processing.request.payload;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBoundDeletingClaim;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class LinkedRoleBoundsDeletingClaim implements RoleBoundDeletingClaim {

    private final boolean deleteAll;

    private final Set<String> authoritiesIds;
}
