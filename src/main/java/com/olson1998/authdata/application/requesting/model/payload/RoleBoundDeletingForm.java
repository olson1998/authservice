package com.olson1998.authdata.application.requesting.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBoundDeletingClaim;
import lombok.Getter;

import java.util.Set;

@Getter
public class RoleBoundDeletingForm implements RoleBoundDeletingClaim {

    private final boolean deleteAll;

    private final Set<String> authoritiesIds;

    @JsonCreator
    public RoleBoundDeletingForm(@JsonProperty(value = "ids") Set<String> authoritiesIds) {
        this.authoritiesIds = authoritiesIds;
        this.deleteAll = authoritiesIds == null;
    }
}
