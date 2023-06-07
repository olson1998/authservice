package com.olson1998.authdata.domain.model.mapping.entity;

import com.olson1998.authdata.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.AuthorityTimestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DomainAuthorityTimestamp implements AuthorityTimestamp {
    
    private final String id;
    
    private final Long expireTime;

    public DomainAuthorityTimestamp(ExtendedAuthorityTimestamp extendedAuthorityTimestamp) {
        this.id=extendedAuthorityTimestamp.getId();
        this.expireTime=extendedAuthorityTimestamp.getExpireTime();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AuthorityTimestamp authorityTimestamp){
            return equals(authorityTimestamp);
        }
        return false;
    }

    @Override
    public boolean equals(AuthorityTimestamp authorityTimestamp) {
        boolean timestampMatch;
        if(authorityTimestamp != null) {
            if(expireTime != null && authorityTimestamp.getExpireTime() != null){
                timestampMatch = expireTime.equals(authorityTimestamp.getExpireTime());
                return timestampMatch && id.equals(authorityTimestamp.getId());
            }else {
                return id.equals(authorityTimestamp.getId());
            }
        }
        return false;
    }

}
