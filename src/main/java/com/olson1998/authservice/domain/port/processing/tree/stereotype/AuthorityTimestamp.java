package com.olson1998.authservice.domain.port.processing.tree.stereotype;

public interface AuthorityTimestamp {

    String getId();

    Long getExpireTime();

    @Override
    boolean equals(Object authorityTimestamp);

    boolean equals(AuthorityTimestamp authorityTimestamp);
}
