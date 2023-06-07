package com.olson1998.authdata.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.model.processing.report.payload.DomainSavedMembership;
import com.olson1998.authdata.domain.port.data.stereotype.UserMembership;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipBindReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class DomainUserMembershipSavingReport implements UserMembershipBindReport {

    @JsonProperty(value = "request_id")
    private final UUID requestId;

    @JsonProperty(value = "user_id")
    private final Long userId;

    @JsonProperty(value = "saved_memberships")
    private final List<UserMembershipClaim> savedMembershipsClaims;

    public DomainUserMembershipSavingReport(UUID requestId, Long userId, List<UserMembership> savedMembershipList) {
        this.requestId = requestId;
        this.userId = userId;
        this.savedMembershipsClaims = savedMembershipList.stream()
                .map(DomainSavedMembership::new)
                .map(UserMembershipClaim.class::cast)
                .toList();
    }
}
