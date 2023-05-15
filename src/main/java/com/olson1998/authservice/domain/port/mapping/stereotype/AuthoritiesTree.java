package com.olson1998.authservice.domain.port.mapping.stereotype;

import java.util.Set;

public interface AuthoritiesTree {

    long getUserId();

    Set<RoleTimestamp> getRoleTimestamps();

}
