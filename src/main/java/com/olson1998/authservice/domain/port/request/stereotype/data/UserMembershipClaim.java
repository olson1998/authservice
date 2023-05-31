package com.olson1998.authservice.domain.port.request.stereotype.data;

public interface UserMembershipClaim {

    Long getUserId();

    long getCompanyNumber();

    String getRegionId();

    Long getGroupId();

    Long getTeamId();

    void setUserId(long userId);
}
