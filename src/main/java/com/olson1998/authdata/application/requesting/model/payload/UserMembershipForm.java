package com.olson1998.authdata.application.requesting.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.Getter;
import lombok.Setter;

import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.*;

@Getter
public class UserMembershipForm implements UserMembershipClaim {

    private final long companyNumber;

    private final String regionId;

    private final Long groupId;

    private final Long teamId;

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
