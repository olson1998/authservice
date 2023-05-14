package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.request.entity.UserMembershipClaim;

import java.util.Set;

public interface UserMembershipDataRepository {

    int deleteUserMembership(long userId);

    void saveUserMemberships(Set<UserMembershipClaim> claims);

}
