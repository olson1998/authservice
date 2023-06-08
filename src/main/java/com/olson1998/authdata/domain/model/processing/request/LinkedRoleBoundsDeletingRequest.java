package com.olson1998.authdata.domain.model.processing.request;

import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBoundDeletingClaim;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Getter
@RequiredArgsConstructor
public class LinkedRoleBoundsDeletingRequest implements RoleBoundDeletingRequest {

    private final boolean deleteAll = true;

    private final UUID id;

    private final Map<String, RoleBoundDeletingClaim> roleBoundsMap;

    public LinkedRoleBoundsDeletingRequest(RoleDeletingRequest request) {
        this.id = request.getId();
        this.roleBoundsMap = new HashMap<>();
        request.getRoleIdSet().forEach(roleId -> roleBoundsMap.put(roleId, claim()));
    }

    private RoleBoundDeletingClaim claim(){
        return new RoleBoundDeletingClaim() {
            @Override
            public boolean isDeleteAll() {
                return true;
            }

            @Override
            public Set<String> getAuthoritiesIds() {
                return null;
            }
        };
    }
}
