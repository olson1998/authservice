package com.olson1998.authdata.application.datasource.repository.tenant.spring;

import com.olson1998.authdata.application.datasource.entity.tenant.RoleBindingData;
import com.olson1998.authdata.application.datasource.entity.tenant.id.RoleBindingJunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleBindingJpaRepository extends JpaRepository<RoleBindingData, RoleBindingJunction> {

    @Query("SELECT DISTINCT rb FROM RoleBindingData rb WHERE rb.junction.authorityId IN :authoritiesIds")
    Set<RoleBindingData> selectRoleBoundsByAuthoritiesIds(Set<String> authoritiesIds);

    @Modifying
    @Query("DELETE FROM RoleBindingData rb WHERE rb.junction.roleId=:roleId AND rb.junction.authorityId IN :authoritiesIds")
    int deleteRoleBindingsByRoleIdAndSetOfAuthorities(String roleId, Set<String> authoritiesIds);

    @Modifying
    @Query("DELETE FROM RoleBindingData rb WHERE rb.junction.roleId=:roleId")
    int deleteRoleBindingByRolesIdSet(String roleId);

}
