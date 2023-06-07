package com.olson1998.authdata.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipDeletingReport;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class DomainUserMembershipDeletingReport implements UserMembershipDeletingReport {

    @JsonProperty(value = "request_id")
    private UUID requestId;

    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "del_region_memberships")
    private int deletedRegionMemberships;

    @JsonProperty(value = "del_group_memberships")
    private int deletedGroupMemberships;

    @JsonProperty(value = "del_team_memberships")
    private int deletedTeamMemberships;
}
