package com.olson1998.authdata.application.requesting.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.data.stereotype.UserMembership;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import static com.olson1998.authdata.application.requesting.model.AbstractCommonJsonValues.*;

@Getter
@EqualsAndHashCode
public class UserMembershipForm implements UserMembershipClaim {

    private final Long companyNumber;

    private final String regionId;

    private final Long groupId;

    private final Long teamId;

    @JsonCreator
    public UserMembershipForm(@JsonProperty(value = COMPANY_NUMBER) Long companyNumber,
                              @JsonProperty(value = REGION_ID) String regionId,
                              @JsonProperty(value = GROUP_ID) Long groupId,
                              @JsonProperty(value = TEAM_ID) Long teamId) {
        this.companyNumber = companyNumber;
        this.regionId = regionId;
        this.groupId = groupId;
        this.teamId = teamId;
    }

    @Override
    public boolean isMatching(@NonNull UserMembership userMembership) {
        boolean sameCono = false;
        boolean sameRegid =false;
        boolean sameGrpid = false;
        boolean sameTmid = false;
        if(userMembership.getCompanyNumber() != null && this.companyNumber != null){
            sameCono = userMembership.getCompanyNumber().equals(this.companyNumber);
        } else if (userMembership.getCompanyNumber() == null && this.companyNumber == null) {
            sameCono = true;
        }
        if(userMembership.getGroupId() != null && this.groupId != null){
            sameGrpid = userMembership.getGroupId().equals(this.groupId);
        } else if (userMembership.getGroupId() == null && this.groupId == null) {
            sameGrpid = true;
        }
        if(userMembership.getRegionId() != null && this.regionId != null){
            sameRegid = userMembership.getRegionId().equals(this.regionId);
        } else if (userMembership.getRegionId() == null && this.regionId == null) {
            sameRegid = true;
        }
        if(userMembership.getTeamId() != null && this.teamId != null){
            sameTmid = userMembership.getTeamId().equals(this.teamId);
        } else if (userMembership.getTeamId() == null && this.teamId == null) {
            sameTmid = true;
        }
        return sameCono && sameGrpid && sameRegid && sameTmid;
    }
}
