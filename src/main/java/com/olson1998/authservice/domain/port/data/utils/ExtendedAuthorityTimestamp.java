package com.olson1998.authservice.domain.port.data.utils;

import com.olson1998.authservice.domain.port.mapping.stereotype.AuthorityTimestamp;

public interface ExtendedAuthorityTimestamp extends AuthorityTimestamp {

    long getUserId();

    String getRoleId();

    long getRoleTimestamp();
}
