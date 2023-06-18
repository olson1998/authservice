package com.olson1998.authdata.domain.port.processing.request.stereotype.payload;

import com.olson1998.authdata.domain.port.data.stereotype.Authority;

public interface AuthorityDetails {

    String getName();

    String getToken();

    Integer getLevel();

    Long getExpiringTime();

    boolean equals(AuthorityDetails authorityDetails);

    boolean isMatching(Authority authority);
}
