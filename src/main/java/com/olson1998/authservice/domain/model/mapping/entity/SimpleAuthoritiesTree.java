package com.olson1998.authservice.domain.model.mapping.entity;

import com.olson1998.authservice.domain.port.mapping.stereotype.AuthoritiesTree;
import com.olson1998.authservice.domain.port.mapping.stereotype.RoleTimestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class SimpleAuthoritiesTree implements AuthoritiesTree {

    private final long userId;

    private final Set<RoleTimestamp> roleTimestamps;

}
