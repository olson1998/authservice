package com.olson1998.authservice.application.requesting.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.Getter;

import static com.olson1998.authservice.application.requesting.entity.data.AbstractCommonJsonValues.*;

@Getter
public class UserMembershipForm implements UserMembershipClaim {

    private Long userId = 1L;

    private final long companyNumber;

    private final String regionId;

    private final Long groupId;

    private final Long teamId;

    @Override
    public void setUserId(long userId) {
        this.userId=userId;
    }

    @JsonCreator
    public UserMembershipForm(@JsonProperty(value = COMPANY_NUMBER, required = true) long companyNumber,
                              @JsonProperty(value = REGION_ID) String regionId,
                              @JsonProperty(value = GROUP_ID) Long groupId,
                              @JsonProperty(value = TEAM_ID) Long teamId) {
        this.companyNumber = companyNumber;
        this.regionId = regionId;
        this.groupId = groupId;
        this.teamId = teamId;
    }
}
