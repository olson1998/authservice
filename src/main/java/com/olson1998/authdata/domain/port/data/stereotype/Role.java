package com.olson1998.authdata.domain.port.data.stereotype;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;

public interface Role {

    String getId();

    String getName();

    Long getUserId();

    Long getCompanyNumber();

    String getSubject();

    String getRegionId();

    Long getGroupId();

    Long getTeamId();

    Long getTimestamp();

    boolean isMatching(RoleDetails roleDetails);
}
