package com.olson1998.authservice.application.datasource.entity.utils;

import com.olson1998.authservice.domain.port.data.utils.ExtendedAuthorityTimestamp;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class ExtendedAuthorityTimestampData implements ExtendedAuthorityTimestamp {

    @Getter
    private final long userId;

    @Getter
    private final String roleId;

    private final String authorityId;

    @Getter
    private final long roleTimestamp;

    private final Long authorityExpireTime;


    @Override
    public String getId() {
        return authorityId;
    }

    @Override
    public Long getExpireTime() {
        return authorityExpireTime;
    }
}
