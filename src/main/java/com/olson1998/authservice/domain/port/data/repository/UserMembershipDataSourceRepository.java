package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.request.stereotype.data.UserMembershipClaim;

import java.util.Set;

public interface UserMembershipDataSourceRepository {

    int deleteUserMembership(long userId);

    void saveUserMemberships(Set<UserMembershipClaim> claims);

}
