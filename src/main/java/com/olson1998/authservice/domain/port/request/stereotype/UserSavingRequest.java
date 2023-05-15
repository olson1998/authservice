package com.olson1998.authservice.domain.port.request.stereotype;

import com.olson1998.authservice.domain.port.request.data.UserDetails;
import com.olson1998.authservice.domain.port.request.data.UserMembershipClaim;

import java.util.Set;

public interface UserSavingRequest extends Request{

    UserDetails getUserDetails();

    Set<UserMembershipClaim> getMembershipClaims();

}
