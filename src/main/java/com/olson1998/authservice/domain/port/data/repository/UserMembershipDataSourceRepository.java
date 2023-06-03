package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.UserMembershipClaim;

import java.util.Set;

public interface UserMembershipDataSourceRepository {

    int deleteUserMembership(long userId);

    void saveUserMemberships(Set<UserMembershipClaim> claims);

}
