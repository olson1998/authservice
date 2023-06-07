package com.olson1998.authdata.application.datasource.repository.jpa;

import com.olson1998.authdata.application.datasource.entity.UserMembershipData;
import com.olson1998.authdata.application.datasource.entity.id.UserMembershipJunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserMembershipJpaRepository extends JpaRepository<UserMembershipData, UserMembershipJunction> {

    @Query("DELETE FROM UserMembershipData um WHERE um.junction.userId=:userId")
    int deleteAllUserMemberships(long userId);

    @Query("DELETE FROM UserMembershipData um WHERE um.junction.userId=:userId AND um.junction.regionId IN :regionIdsSet")
    int deleteUserRegionMemberships(Long userId, Set<String> regionIdsSet);

    @Query("DELETE FROM UserMembershipData um WHERE um.junction.userId=:userId AND um.junction.groupId IN :groupIdsSet")
    int deleteUserGroupMemberships(Long userId, Set<Long> groupIdsSet);

    @Query("DELETE FROM UserMembershipData um WHERE um.junction.userId=:userId AND um.junction.regionId IN :teamIsSet")
    int deleteUserTeamMemberships(Long userId, Set<Long> teamIsSet);
}
