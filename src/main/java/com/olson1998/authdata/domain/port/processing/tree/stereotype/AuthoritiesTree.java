package com.olson1998.authdata.domain.port.processing.tree.stereotype;

import java.util.Set;

public interface AuthoritiesTree {

    long getUserId();

    Set<RoleTimestamp> getRoleTimestamps();

}
