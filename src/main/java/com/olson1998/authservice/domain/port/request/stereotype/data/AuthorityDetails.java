package com.olson1998.authservice.domain.port.request.stereotype.data;

public interface AuthorityDetails {

    String getName();

    String getToken();

    Integer getLevel();

    Long getExpiringTime();
}
