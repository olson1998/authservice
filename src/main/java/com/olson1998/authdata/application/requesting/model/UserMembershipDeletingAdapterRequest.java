package com.olson1998.authdata.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipDeletingRequest;
import lombok.Getter;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.ID;
import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.USER_ID;

@Getter
public class UserMembershipDeletingAdapterRequest implements UserMembershipDeletingRequest {

    private final UUID id;

    private final Long userId;
    private final Set<String> regionMemberships;

    private final Set<Long> groupMemberships;

    private final Set<Long> teamMemberships;

    @JsonCreator
    public UserMembershipDeletingAdapterRequest(@JsonProperty(value = ID, required = true) UUID id,
                                                @JsonProperty(value = USER_ID, required = true) Long userId,
                                                @JsonProperty(value = "region_memberships") Set<String> regionMemberships,
                                                @JsonProperty(value = "group_memberships") Set<Long> groupMemberships,
                                                @JsonProperty(value = "team_memberships") Set<Long> teamMemberships) {
        this.id = id;
        this.userId = userId;
        this.regionMemberships = returnEmptySetIfNull(regionMemberships);
        this.groupMemberships = returnEmptySetIfNull(groupMemberships);
        this.teamMemberships = returnEmptySetIfNull(teamMemberships);
    }

    private <P> Set<P> returnEmptySetIfNull(Set<P> givenSet){
        return Objects.requireNonNullElse(givenSet, Collections.emptySet());
    }
}
