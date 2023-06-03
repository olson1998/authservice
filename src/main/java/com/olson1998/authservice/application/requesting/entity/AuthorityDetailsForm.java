package com.olson1998.authservice.application.requesting.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.Getter;

@Getter
public class AuthorityDetailsForm implements AuthorityDetails {

    private final String name;

    private final String token;

    private final Integer level;

    private final Long expiringTime;

    @JsonCreator
    public AuthorityDetailsForm(@JsonProperty(value = "name", required = true) String name,
                                @JsonProperty("token") String token,
                                @JsonProperty("level") Integer level,
                                @JsonProperty("exp") Long expiringTime) {
        this.name = name;
        this.token = token;
        this.level = level;
        this.expiringTime = expiringTime;
    }
}
