package com.olson1998.authdata.domain.port.processing.report.stereotype;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;

import java.util.List;

public interface UserMembershipBindReport extends ProcessingReport{

    Long getUserId();

    List<UserMembershipClaim> getSavedMembershipsClaims();
}
