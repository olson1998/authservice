package com.olson1998.authdata.domain.port.data.stereotype;

public interface UserPassword {

    Long getUserId();

    String getPassword();

    Long getExpireTime();

    boolean isExpiring();
}
