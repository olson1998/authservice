package com.olson1998.authdata.domain.model.mapping.entity;

import com.olson1998.authdata.domain.port.processing.tree.stereotype.AuthoritiesTree;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.RoleTimestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class DomainAuthoritiesTree implements AuthoritiesTree {

    private final long userId;

    private final Set<RoleTimestamp> roleTimestamps;

}
