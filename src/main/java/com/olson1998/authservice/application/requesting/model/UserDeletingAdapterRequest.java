package com.olson1998.authservice.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.domain.port.request.stereotype.UserDeletingRequest;
import lombok.Getter;

import java.util.UUID;

import static com.olson1998.authservice.application.requesting.model.AbstractCommonJsonValues.ID;
import static com.olson1998.authservice.application.requesting.model.AbstractCommonJsonValues.USER_ID;

@Getter
public class UserDeletingAdapterRequest implements UserDeletingRequest {

    private final UUID id;

    private final long userId;

    @JsonCreator
    public UserDeletingAdapterRequest(@JsonProperty(value = ID, required = true) UUID id,
                                      @JsonProperty(value = USER_ID, required = true) long userId) {
        this.id = id;
        this.userId = userId;
    }
}
