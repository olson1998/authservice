package com.olson1998.authservice.domain.port.request.model;

import com.olson1998.authservice.domain.port.request.entity.UserDetails;
import com.olson1998.authservice.domain.port.request.entity.UserMembershipClaim;

import java.util.Set;

public interface UserSavingRequest extends Request{

    UserDetails getUserDetails();

    Set<UserMembershipClaim> getMembershipClaims();

}
