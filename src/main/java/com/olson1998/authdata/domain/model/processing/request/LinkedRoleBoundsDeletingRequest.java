package com.olson1998.authdata.domain.model.processing.request;

import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleDeletingRequest;
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

    private final Map<String, Set<String>> roleBoundsMap;

    public LinkedRoleBoundsDeletingRequest(RoleDeletingRequest request) {
        this.id = request.getId();
        this.roleBoundsMap = new HashMap<>();
        request.getRoleIdSet().forEach(roleId -> roleBoundsMap.put(roleId, null));
    }

    @Override
    public void setDeleteAll(boolean deleteAll) {
        log.error("setter not allowed...");
    }
}
