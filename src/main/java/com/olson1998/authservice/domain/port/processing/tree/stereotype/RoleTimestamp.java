package com.olson1998.authservice.domain.port.processing.tree.stereotype;

import java.util.Set;

public interface RoleTimestamp {

    String getId();

    long getTimestamp();

    Set<AuthorityTimestamp> getAuthoritiesTimestamps();

    @Override
    boolean equals(Object obj);

    boolean equals(RoleTimestamp roleTimestamp);
}
