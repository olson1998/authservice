package com.olson1998.authdata.application.datasource.repository.jpa;

import com.olson1998.authdata.application.datasource.entity.RoleData;
import com.olson1998.authdata.application.datasource.entity.UserData;
import com.olson1998.authdata.application.datasource.entity.values.SecretDigest;
import com.olson1998.authdata.application.datasource.entity.values.ExtendedAuthorityTimestampData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserJpaRepository extends JpaRepository<UserData, Long> {

    @Query("SELECT u FROM UserData u WHERE u.username=:username")
    Optional<UserData> selectUserByUsername(String username);

    @Query("SELECT u.secretDigest FROM UserData u WHERE u.username=:username")
    Optional<SecretDigest> selectUserPasswordDigest(String username);

    @Query("SELECT r FROM UserData u " +
            "LEFT OUTER JOIN UserMembershipData mb ON u.id=mb.junction.userId " +
            "LEFT OUTER JOIN RoleData r ON " +
            "(mb.junction.userId=r.userId AND r.subject='PRIVATE') OR " +
            "(mb.junction.companyNumber=r.companyNumber AND r.subject='COMPANY') OR " +
            "(mb.junction.regionId=r.regionId AND r.subject='REGION') OR " +
            "(mb.junction.groupId=r.groupId AND r.subject='GROUP') OR " +
            "(mb.junction.teamId=r.teamId AND r.subject='TEAM') " +
            "WHERE u.id=:userId")
    Set<RoleData> selectUserRoles(long userId);

    @Query("SELECT new com.olson1998.authdata.application.datasource.entity.values.ExtendedAuthorityTimestampData" +
            "(u.id, r.id, a.id, r.timestamp, a.expiringTime) " +
            "FROM UserData u " +
            "LEFT OUTER JOIN UserMembershipData mb ON u.id=mb.junction.userId " +
            "LEFT OUTER JOIN RoleData r ON " +
            "(mb.junction.userId=r.userId AND r.subject='PRIVATE') OR " +
            "(mb.junction.companyNumber=r.companyNumber AND r.subject='COMPANY') OR " +
            "(mb.junction.regionId=r.regionId AND r.subject='REGION') OR " +
            "(mb.junction.groupId=r.groupId AND r.subject='GROUP') OR " +
            "(mb.junction.teamId=r.teamId AND r.subject='TEAM') " +
            "LEFT OUTER JOIN RoleBindingData rb ON r.id=rb.junction.roleId " +
            "LEFT OUTER JOIN AuthorityData a ON a.id=rb.junction.authorityId " +
            "WHERE u.id=:userId")
    Set<ExtendedAuthorityTimestampData> selectUserAuthoritiesTimestamps(long userId);

    @Modifying
    @Query("DELETE FROM UserData u WHERE u.id=:id")
    int deleteUserById(long id);

}
