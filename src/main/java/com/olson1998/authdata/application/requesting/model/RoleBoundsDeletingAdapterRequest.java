package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.application.requesting.model.payload.RoleBoundDeletingForm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBoundDeletingClaim;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RoleBoundsDeletingAdapterRequest extends AbstractAdapterRequest implements RoleBoundDeletingRequest {

    private final boolean deleteAll = false;

    private final Map<String, RoleBoundDeletingClaim> roleBoundsMap;

    public RoleBoundsDeletingAdapterRequest(Map<String, RoleBoundDeletingForm> roleBoundDeletingForms) {
        this.roleBoundsMap = new HashMap<>();
        this.roleBoundsMap.putAll(roleBoundDeletingForms);
    }

}
