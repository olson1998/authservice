package com.olson1998.authdata.application.datasource.repository.tenant.spring;

import com.olson1998.authdata.application.datasource.entity.tenant.RoleData;
import com.olson1998.authdata.application.datasource.entity.tenant.UserData;
import com.olson1998.authdata.application.datasource.entity.tenant.values.ExtendedAuthorityTimestampData;
import com.olson1998.authdata.application.datasource.entity.tenant.values.UserAuthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserJpaRepository extends JpaRepository<UserData, Long> {

    @Query("SELECT new com.olson1998.authdata.application.datasource.entity.tenant.values.UserAuthData(" +
            "u.id," +
            "u.username," +
            "pass.password," +
            "u.idIssueTime," +
            "u.expireTime," +
            "pass.expireTime," +
            "u.enabled," +
            "CASE WHEN COUNT(ban.id) > 0 THEN true ELSE false END) " +
            "FROM UserData u " +
            "LEFT OUTER JOIN UserPasswordData pass " +
            "ON u.id=pass.userId " +
            "LEFT OUTER JOIN UserBansData ban " +
            "ON u.id=ban.userId " +
            "WHERE u.username=:username")
    Optional<UserAuthData> selectUserAuthDataByUsername(String username);

    @Query("SELECT r FROM UserData u " +
            "LEFT OUTER JOIN UserMembershipData mb ON u.id=mb.junction.userId " +
            "LEFT OUTER JOIN RoleData r ON " +
            "(u.id=r.userId AND r.subject='PRIVATE') OR " +
            "(mb.junction.companyNumber=r.companyNumber AND r.subject='COMPANY') OR " +
            "(mb.junction.regionId=r.regionId AND r.subject='REGION') OR " +
            "(mb.junction.groupId=r.groupId AND r.subject='GROUP') OR " +
            "(mb.junction.teamId=r.teamId AND r.subject='TEAM') " +
            "WHERE u.id=:userId")
    Set<RoleData> selectUserRoles(long userId);

    @Query("SELECT new com.olson1998.authdata.application.datasource.entity.tenant.values.ExtendedAuthorityTimestampData" +
            "(u.id, r.id, a.id, r.timestamp, a.expiringTime) " +
            "FROM UserData u " +
            "LEFT OUTER JOIN UserMembershipData mb ON u.id=mb.junction.userId " +
            "LEFT OUTER JOIN RoleData r ON " +
            "(u.id=r.userId AND r.subject='PRIVATE') OR " +
            "(mb.junction.companyNumber=r.companyNumber AND r.subject='COMPANY') OR " +
            "(mb.junction.regionId=r.regionId AND r.subject='REGION') OR " +
            "(mb.junction.groupId=r.groupId AND r.subject='GROUP') OR " +
            "(mb.junction.teamId=r.teamId AND r.subject='TEAM') " +
            "LEFT OUTER JOIN RoleBindingData rb ON r.id=rb.junction.roleId " +
            "LEFT OUTER JOIN AuthorityData a ON a.id=rb.junction.authorityId " +
            "WHERE u.id=:userId")
    Set<ExtendedAuthorityTimestampData> selectUserAuthoritiesTimestamps(long userId);

    @Query("DELETE FROM UserData u WHERE u.id=:id")
    int deleteUserById(long id);

}
