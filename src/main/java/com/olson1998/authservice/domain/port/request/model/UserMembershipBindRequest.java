package com.olson1998.authservice.domain.port.request.model;

import java.util.Set;

public interface UserMembershipBindRequest {

    long getUserId();

    Set<String> getBindingRoleIdSet();
}
