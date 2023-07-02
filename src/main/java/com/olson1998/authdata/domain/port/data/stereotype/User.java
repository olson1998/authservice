package com.olson1998.authdata.domain.port.data.stereotype;

public interface User {

    Long getId();

    String getUsername();

    Long getExpireTime();

    Long getIdIssuingTime();

    boolean isEnabled();

    boolean isExpiring();
}
