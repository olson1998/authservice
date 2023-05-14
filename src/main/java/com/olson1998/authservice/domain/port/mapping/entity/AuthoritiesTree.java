package com.olson1998.authservice.domain.port.mapping.entity;

import java.util.Set;

public interface AuthoritiesTree {

    long getUserId();

    Set<RoleTimestamp> getRoleTimestamps();

}
