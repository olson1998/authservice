package com.olson1998.authdata.domain.model.mapping.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.AuthoritiesTree;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.RoleTimestamp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class DomainAuthoritiesTree implements AuthoritiesTree {

    @JsonProperty(value = "user_id")
    private final long userId;

    @JsonProperty(value = "timestamps")
    private final Set<RoleTimestamp> roleTimestamps;

}
