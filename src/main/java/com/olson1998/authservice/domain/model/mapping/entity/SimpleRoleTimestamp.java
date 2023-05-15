package com.olson1998.authservice.domain.model.mapping.entity;

import com.olson1998.authservice.domain.port.mapping.stereotype.AuthorityTimestamp;
import com.olson1998.authservice.domain.port.mapping.stereotype.RoleTimestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Getter

@RequiredArgsConstructor
public class SimpleRoleTimestamp implements RoleTimestamp {

    private final String id;

    private final long timestamp;

    private final Set<AuthorityTimestamp> authoritiesTimestamps;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleRoleTimestamp that = (SimpleRoleTimestamp) o;
        return timestamp == that.timestamp && id.equals(that.id) && authoritiesTimestamps.containsAll(that.authoritiesTimestamps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, authoritiesTimestamps);
    }

    public void addAll(Collection<AuthorityTimestamp> authorityTimestamps){
        this.authoritiesTimestamps.addAll(authorityTimestamps);
    }

}
