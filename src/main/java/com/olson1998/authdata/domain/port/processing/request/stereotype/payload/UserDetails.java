package com.olson1998.authdata.domain.port.processing.request.stereotype.payload;

import java.time.Duration;

public interface UserDetails {

    String getUsername();

    String getPassword();

    boolean isEnabled();

    Duration getUserExpDuration();

    Duration getPasswordExpDuration();

    boolean isUserPasswordExpiring();

    boolean isUserExpiring();

}
