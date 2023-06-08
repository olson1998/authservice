package com.olson1998.authdata.application.requesting.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class AuthorityDetailsForm implements AuthorityDetails {

    @JsonProperty(value = "name")
    private final String name;

    @JsonProperty("token")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String token;

    @JsonProperty("level")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer level;

    @JsonProperty("exp")
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AuthorityDetails authorityDetails){
            return equals(authorityDetails);
        }else {
            return false;
        }
    }

    @Override
    public boolean equals(AuthorityDetails authorityDetails) {
        boolean eqToken = false;
        boolean eqLvl = false;
        boolean eqExpTime = false;
        if(token != null && authorityDetails.getToken() != null){
            eqToken = token.equals(authorityDetails.getToken());
        }else {
            eqToken = true;
        }
        if(level != null && authorityDetails.getLevel() != null){
            eqLvl = level.equals(authorityDetails.getLevel());
        }else {
            eqLvl = true;
        }
        if(expiringTime != null && authorityDetails.getExpiringTime() != null){
            eqExpTime = expiringTime.equals(authorityDetails.getExpiringTime());
        }else {
            eqExpTime = true;
        }
        return eqToken &&
                eqLvl &&
                eqExpTime &&
                name.equals(authorityDetails.getName());
    }
}
