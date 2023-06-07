package com.olson1998.authdata.domain.port.processing.request.stereotype;

import java.util.Set;

public interface UserMembershipDeletingRequest extends Request {

    Long getUserId();

    Set<String> getRegionMemberships();

    Set<Long> getGroupMemberships();

    Set<Long> getTeamMemberships();

}
