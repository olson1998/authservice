package com.olson1998.authservice.application.datasource.repository.jpa;

import com.olson1998.authservice.application.datasource.entity.RoleAuthorityData;
import com.olson1998.authservice.application.datasource.entity.RoleData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleData, Long> {

    @Query("SELECT CASE WHEN (r.timestamp=:timestamp AND a.expiringTime >=:now) THEN 1 ELSE 0 END " +
            "FROM RoleData r " +
            "LEFT OUTER JOIN RoleBindingData rb ON r.id=rb.junction.roleId " +
            "LEFT OUTER JOIN RoleAuthorityData a ON a.authorityId=rb.junction.authorityId " +
            "WHERE r.id=:roleId" +
            "")
    boolean selectCaseIfTimestampIsTopical(String roleId, long timestamp, long now);

    @Query("SELECT a FROM RoleData r " +
            "LEFT OUTER JOIN RoleBindingData rb ON r.id=rb.junction.roleId " +
            "LEFT OUTER JOIN RoleAuthorityData a ON a.authorityId=rb.junction.authorityId " +
            "WHERE r.id IN :rolesId AND " +
            "(a.expiringTime IS NULL OR a.expiringTime >= :now)")
    Set<RoleAuthorityData> selectRolesAuthorities(Set<String> rolesId, long now);

    @Query("UPDATE RoleData r SET r.timestamp=:timestamp WHERE r.id=:roleId")
    int updateRoleTimestamp(String roleId, long timestamp);
}
