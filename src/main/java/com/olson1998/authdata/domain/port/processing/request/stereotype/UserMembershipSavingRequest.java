package com.olson1998.authdata.domain.port.processing.request.stereotype;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;

import java.util.Set;

public interface UserMembershipSavingRequest extends Request{

    Long getUserId();

    Set<UserMembershipClaim> getUserMembershipClaims();
}
