package com.olson1998.authdata.domain.port.processing.request.stereotype;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBoundDeletingClaim;

import java.util.Map;

public interface RoleBoundDeletingRequest extends Request{

    boolean isDeleteAll();

    Map<String, RoleBoundDeletingClaim> getRoleBoundsMap();

}
