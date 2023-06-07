package com.olson1998.authdata.domain.port.processing.request.stereotype.payload;

public interface AuthorityDetails {

    String getName();

    String getToken();

    Integer getLevel();

    Long getExpiringTime();

    boolean equals(AuthorityDetails authorityDetails);
}
