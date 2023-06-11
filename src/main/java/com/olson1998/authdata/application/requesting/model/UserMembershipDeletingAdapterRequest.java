package com.olson1998.authdata.application.requesting.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipDeletingRequest;
import lombok.Getter;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Getter
public class UserMembershipDeletingAdapterRequest extends AbstractUserAdapterRequest implements UserMembershipDeletingRequest {
    private final Set<String> regionMemberships;

    private final Set<Long> groupMemberships;

    private final Set<Long> teamMemberships;

    @JsonCreator
    public UserMembershipDeletingAdapterRequest(@JsonProperty(value = "region_memberships") Set<String> regionMemberships,
                                                @JsonProperty(value = "group_memberships") Set<Long> groupMemberships,
                                                @JsonProperty(value = "team_memberships") Set<Long> teamMemberships) {
        this.regionMemberships = returnEmptySetIfNull(regionMemberships);
        this.groupMemberships = returnEmptySetIfNull(groupMemberships);
        this.teamMemberships = returnEmptySetIfNull(teamMemberships);
    }

    private <P> Set<P> returnEmptySetIfNull(Set<P> givenSet){
        return Objects.requireNonNullElse(givenSet, Collections.emptySet());
    }
}
