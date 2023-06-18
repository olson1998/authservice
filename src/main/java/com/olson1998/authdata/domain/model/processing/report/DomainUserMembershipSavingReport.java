package com.olson1998.authdata.domain.model.processing.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipBindReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class DomainUserMembershipSavingReport implements UserMembershipBindReport {

    @JsonProperty(value = "request_id")
    private final UUID requestId;

    @JsonProperty(value = "user_id")
    private final Long userId;

    @JsonProperty(value = "saved_memberships")
    private final Map<String, UserMembershipClaim> savedMembershipsClaims;


}
