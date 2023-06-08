package com.olson1998.authdata.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.application.requesting.model.payload.RoleBoundDeletingForm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBoundDeletingClaim;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.ID;

@Getter
@Setter
public class RoleBoundsDeletingAdapterRequest implements RoleBoundDeletingRequest {

    @JsonIgnore
    private final boolean deleteAll = false;

    @JsonProperty(value = ID, required = true)
    private final UUID id;

    @JsonProperty(value = "role_bounds")
    private final Map<String, RoleBoundDeletingClaim> roleBoundsMap;

    @JsonCreator
    public RoleBoundsDeletingAdapterRequest(@JsonProperty(value = ID, required = true) UUID id,
                                            @JsonProperty(value = "authorities")Map<String, RoleBoundDeletingForm> roleBoundDeletingForms) {
        this.id = id;
        this.roleBoundsMap = new HashMap<>();
        this.roleBoundsMap.putAll(roleBoundDeletingForms);
    }
}
