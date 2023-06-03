package com.olson1998.authservice.domain.model.mapping.entity;

import com.olson1998.authservice.domain.port.processing.tree.stereotype.AuthorityTimestamp;
import com.olson1998.authservice.domain.port.processing.tree.stereotype.RoleTimestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Getter

@RequiredArgsConstructor
public class DomainRoleTimestamp implements RoleTimestamp {

    private final String id;

    private final long timestamp;

    private final Set<AuthorityTimestamp> authoritiesTimestamps;

    @Override
    public boolean equals(Object o) {
        if(o instanceof RoleTimestamp roleTimestamp){
            return equals(roleTimestamp);
        }else {
            return false;
        }
    }

    @Override
    public boolean equals(RoleTimestamp roleTimestamp) {
        return id.equals(roleTimestamp.getId()) &&
                timestamp == roleTimestamp.getTimestamp() &&
                authoritiesTimestamps.containsAll(roleTimestamp.getAuthoritiesTimestamps());
    }

    public void addAll(Collection<AuthorityTimestamp> authorityTimestamps){
        this.authoritiesTimestamps.addAll(authorityTimestamps);
    }

}
