package com.olson1998.authdata.domain.port.processing.request.stereotype.payload;

import com.olson1998.authdata.domain.port.data.stereotype.UserMembership;

public interface UserMembershipClaim {

    Long getCompanyNumber();

    String getRegionId();

    Long getGroupId();

    Long getTeamId();

    boolean isMatching(UserMembership userMembership);

}
