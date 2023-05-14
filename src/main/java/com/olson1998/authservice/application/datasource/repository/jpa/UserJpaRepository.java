package com.olson1998.authservice.application.datasource.repository.jpa;

import com.olson1998.authservice.application.datasource.entity.RoleData;
import com.olson1998.authservice.application.datasource.entity.UserData;
import com.olson1998.authservice.application.datasource.entity.utils.PasswordDigest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserJpaRepository extends JpaRepository<UserData, Long> {

    @Query("SELECT u FROM UserData u WHERE u.username=:username")
    Optional<UserData> selectUserByUsername(String username);

    @Query("SELECT u.passwordDigest FROM UserData u WHERE u.username=:username")
    Optional<PasswordDigest> selectUserPasswordDigest(String username);

    @Query("SELECT r FROM UserData u " +
            "LEFT OUTER JOIN UserMembershipData mb ON u.id=mb.junction.userId " +
            "LEFT OUTER JOIN RoleData r ON " +
            "(mb.junction.userId=r.userId AND r.subject='PRIVATE') OR " +
            "(mb.junction.companyNumber=r.companyNumber AND r.subject='COMPANY') OR " +
            "(mb.junction.groupId=r.groupId AND r.subject='GROUP') OR " +
            "(mb.junction.teamId=r.teamId AND r.subject='TEAM') " +
            "WHERE u.username=:username")
    Set<RoleData> selectUserRoles(String username);

    @Query("DELETE FROM UserData u WHERE u.username=:username")
    int deleteUserByUsername(String username);
}
