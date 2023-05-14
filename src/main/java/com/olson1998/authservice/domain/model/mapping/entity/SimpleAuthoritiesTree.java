package com.olson1998.authservice.domain.model.mapping.entity;

import com.olson1998.authservice.domain.port.mapping.entity.AuthoritiesTree;
import com.olson1998.authservice.domain.port.mapping.entity.RoleTimestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class SimpleAuthoritiesTree implements AuthoritiesTree {

    private final long userId;

    private final Set<RoleTimestamp> roleTimestamps;

}
