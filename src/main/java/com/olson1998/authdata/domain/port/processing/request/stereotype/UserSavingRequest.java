package com.olson1998.authdata.domain.port.processing.request.stereotype;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;

import java.util.Set;

public interface UserSavingRequest extends Request{

    UserDetails getUserDetails();

    Set<UserMembershipClaim> getMembershipClaims();

}
