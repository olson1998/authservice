package com.olson1998.authservice.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.domain.port.request.entity.UserDetails;
import com.olson1998.authservice.domain.port.request.model.UserSavingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter

@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUserSavingRequest implements UserSavingRequest {

    @JsonProperty(value = "user")
    private UserDetails userDetails;
}
