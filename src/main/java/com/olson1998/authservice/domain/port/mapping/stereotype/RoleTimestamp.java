package com.olson1998.authservice.domain.port.mapping.stereotype;

import java.util.Set;

public interface RoleTimestamp {

    String getId();

    long getTimestamp();

    Set<AuthorityTimestamp> getAuthoritiesTimestamps();
}
