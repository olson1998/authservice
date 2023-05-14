package com.olson1998.authservice.domain.service.tree;

import com.olson1998.authservice.domain.model.mapping.entity.SimpleAuthoritiesTree;
import com.olson1998.authservice.domain.model.mapping.entity.SimpleAuthorityTimestamp;
import com.olson1998.authservice.domain.model.mapping.entity.SimpleRoleTimestamp;
import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authservice.domain.port.mapping.entity.AuthoritiesTree;
import com.olson1998.authservice.domain.port.mapping.entity.AuthorityTimestamp;
import com.olson1998.authservice.domain.port.mapping.entity.RoleTimestamp;
import com.olson1998.authservice.domain.port.mapping.repository.AuthoritiesTreeMapper;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class DomainAuthoritiesTreeMapper implements AuthoritiesTreeMapper {

    @Override
    public AuthoritiesTree map(User user, Set<ExtendedAuthorityTimestamp> extendedAuthorityTimestamps){
        var roleTimestamp = mapRoleTimestamps(extendedAuthorityTimestamps);
        return new SimpleAuthoritiesTree(
                user.getId(),
                roleTimestamp
        );
    }

    private Set<RoleTimestamp> mapRoleTimestamps(Set<ExtendedAuthorityTimestamp> extendedAuthorityTimestamps){
        var roleTimestamps = extendedAuthorityTimestamps.stream()
                .map(this::mapRoleTimestamp)
                .collect(Collectors.toSet());
        roleTimestamps.forEach(timestamp ->{
            var authoritiesTimestamp = extendedAuthorityTimestamps.stream()
                    .filter(extendedAuthorityTimestamp -> filterRoleAuthorities(timestamp, extendedAuthorityTimestamp))
                    .map(this::mapAuthorityTimestamp)
                    .collect(Collectors.toSet());
            timestamp.addAll(authoritiesTimestamp);
        });
        return roleTimestamps.stream()
                .map(this::mapRoleTimestamp)
                .collect(Collectors.toSet());
    }

    private SimpleRoleTimestamp mapRoleTimestamp(ExtendedAuthorityTimestamp extendedAuthorityTimestamp){
        return new SimpleRoleTimestamp(
                extendedAuthorityTimestamp.getRoleId(),
                extendedAuthorityTimestamp.getRoleTimestamp(),
                new HashSet<>()
        );
    }

    private AuthorityTimestamp mapAuthorityTimestamp(ExtendedAuthorityTimestamp extendedAuthorityTimestamp){
        return Objects.requireNonNullElse(
                new SimpleAuthorityTimestamp(extendedAuthorityTimestamp),
                null
        );
    }

    private RoleTimestamp mapRoleTimestamp(SimpleRoleTimestamp simpleRoleTimestamp){
        return Objects.requireNonNullElse(simpleRoleTimestamp, null);
    }

    private boolean filterRoleAuthorities(SimpleRoleTimestamp simpleRoleTimestamp, ExtendedAuthorityTimestamp authorityTimestamp){
        return simpleRoleTimestamp.getId().equals(authorityTimestamp.getRoleId()) &&
                simpleRoleTimestamp.getTimestamp() == authorityTimestamp.getRoleTimestamp();
    }
}
