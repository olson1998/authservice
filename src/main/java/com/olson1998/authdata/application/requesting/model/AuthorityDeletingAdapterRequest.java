package com.olson1998.authdata.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthorityDeletingRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.ID;

@Getter
@Setter
@NoArgsConstructor
public class AuthorityDeletingAdapterRequest implements AuthorityDeletingRequest {

    @JsonProperty(value = ID, required = true)
    private UUID id;

    @JsonProperty(value = "authorities")
    private Set<String> authoritiesIds;
}
