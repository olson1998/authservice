package com.olson1998.authdata.application.datasource.repository.jpa;

import com.olson1998.authdata.application.datasource.entity.AuthorityData;
import com.olson1998.authdata.application.datasource.entity.RoleData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleData, String> {

    @Query("SELECT a FROM RoleData r " +
            "LEFT OUTER JOIN RoleBindingData rb ON r.id=rb.junction.roleId " +
            "LEFT OUTER JOIN AuthorityData a ON a.id=rb.junction.authorityId " +
            "WHERE r.id IN :rolesId AND " +
            "(a.expiringTime IS NULL OR a.expiringTime >= :now)")
    Set<AuthorityData> selectRolesAuthorities(Set<String> rolesId, long now);

    @Modifying
    @Query("UPDATE RoleData r SET r.timestamp=:timestamp WHERE r.id IN :rolesIds")
    int updateRoleTimestamps(Set<String> rolesIds, long timestamp);

    @Modifying
    @Query("DELETE FROM RoleData r WHERE r.subject='PRIVATE' AND r.userId=:userId")
    int deleteAllPrivateRolesByUserId(long userId);

    @Modifying
    @Query("DELETE FROM RoleData r WHERE r.id IN :roleIdsSet")
    int deleteRoleWithGivenId(Set<String> roleIdsSet);

}
