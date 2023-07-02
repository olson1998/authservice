package com.olson1998.authdata.domain.port.data.stereotype;

import java.time.LocalDateTime;

public interface UserBan {

    String getId();

    Long getUserId();

    String getReason();

    LocalDateTime getBanTimestamp();
}
