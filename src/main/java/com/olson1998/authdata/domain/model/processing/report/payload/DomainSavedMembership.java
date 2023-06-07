package com.olson1998.authdata.domain.model.processing.report.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.data.stereotype.UserMembership;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor
public class DomainSavedMembership implements UserMembershipClaim {

    @JsonProperty(value = "cono")
    private final Long companyNumber;

    @JsonProperty(value = "rid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String regionId;

    @JsonProperty(value = "gid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long groupId;

    @JsonProperty(value = "tid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long teamId;

    public DomainSavedMembership(@NonNull UserMembership userMembership) {
        this.companyNumber = userMembership.getCompanyNumber();
        this.regionId = userMembership.getRegionId();
        this.groupId = userMembership.getGroupId();
        this.teamId = userMembership.getTeamId();
    }

}
