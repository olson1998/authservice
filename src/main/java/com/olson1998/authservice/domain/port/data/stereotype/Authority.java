package com.olson1998.authservice.domain.port.data.stereotype;

public interface Authority {

    String getId();

    String getAuthorityName();

    Integer getLevel();

    Long getExpiringTime();

    String getAuthorityToken();

}
