package com.olson1998.authdata.application.datasource.entity.tenant.values;

import com.olson1998.authdata.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.AuthorityTimestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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

    @Override
    public boolean equals(AuthorityTimestamp authorityTimestamp) {
        boolean timestampMatch;
        if(authorityTimestamp != null) {
            if(authorityExpireTime != null && authorityTimestamp.getExpireTime() != null){
                timestampMatch = authorityExpireTime.equals(authorityTimestamp.getExpireTime());
                return timestampMatch && authorityId.equals(authorityTimestamp.getId());
            }else {
                return authorityId.equals(authorityTimestamp.getId());
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AuthorityTimestamp authorityTimestamp){
            return equals(authorityTimestamp);
        }
        return false;
    }
}
