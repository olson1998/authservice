package com.olson1998.authdata.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleDeletingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.ID;

@Getter
@NoArgsConstructor
public class RoleDeletingAdapterRequest implements RoleDeletingRequest {

    @JsonProperty(value = ID, required = true)
    private UUID id;

    @JsonProperty(value = "roles_ids")
    private Set<String> roleIdSet;
}
