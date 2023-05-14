package com.olson1998.authservice.domain.model.mapping.entity;

import com.olson1998.authservice.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authservice.domain.port.mapping.entity.AuthorityTimestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class SimpleAuthorityTimestamp implements AuthorityTimestamp {
    
    private final String id;
    
    private final Long expireTime;

    public SimpleAuthorityTimestamp(ExtendedAuthorityTimestamp extendedAuthorityTimestamp) {
        this.id=extendedAuthorityTimestamp.getId();
        this.expireTime=extendedAuthorityTimestamp.getExpireTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleAuthorityTimestamp that = (SimpleAuthorityTimestamp) o;
        return id.equals(that.id) && Objects.equals(expireTime, that.expireTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, expireTime);
    }
}
