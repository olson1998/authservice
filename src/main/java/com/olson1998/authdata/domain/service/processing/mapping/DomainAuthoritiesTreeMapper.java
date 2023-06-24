package com.olson1998.authdata.domain.service.processing.mapping;

import com.olson1998.authdata.domain.model.mapping.entity.DomainAuthoritiesTree;
import com.olson1998.authdata.domain.model.mapping.entity.DomainAuthorityTimestamp;
import com.olson1998.authdata.domain.model.mapping.entity.DomainRoleTimestamp;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.AuthoritiesTree;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.AuthorityTimestamp;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.RoleTimestamp;
import com.olson1998.authdata.domain.port.processing.tree.repository.AuthoritiesTreeMapper;
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
        return new DomainAuthoritiesTree(
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

    private DomainRoleTimestamp mapRoleTimestamp(ExtendedAuthorityTimestamp extendedAuthorityTimestamp){
        return new DomainRoleTimestamp(
                extendedAuthorityTimestamp.getRoleId(),
                extendedAuthorityTimestamp.getRoleTimestamp(),
                new HashSet<>()
        );
    }

    private AuthorityTimestamp mapAuthorityTimestamp(ExtendedAuthorityTimestamp extendedAuthorityTimestamp){
        return Objects.requireNonNullElse(
                new DomainAuthorityTimestamp(extendedAuthorityTimestamp),
                null
        );
    }

    private RoleTimestamp mapRoleTimestamp(DomainRoleTimestamp domainRoleTimestamp){
        return Objects.requireNonNullElse(domainRoleTimestamp, null);
    }

    private boolean filterRoleAuthorities(DomainRoleTimestamp domainRoleTimestamp, ExtendedAuthorityTimestamp authorityTimestamp){
        return domainRoleTimestamp.getId().equals(authorityTimestamp.getRoleId()) &&
                domainRoleTimestamp.getTimestamp() == authorityTimestamp.getRoleTimestamp();
    }
}
