package com.olson1998.authservice.application.datasource.repository.jpa;

import com.olson1998.authservice.application.datasource.entity.RoleBindingData;
import com.olson1998.authservice.application.datasource.entity.id.RoleBindingJunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleBindingJpaRepository extends JpaRepository<RoleBindingData, RoleBindingJunction> {

    @Query("SELECT CASE WHEN COUNT (rb.junction.roleId) > 1 THEN 1 ELSE 0 END FROM RoleBindingData rb WHERE rb.junction.authorityId=:authorityId")
    boolean selectCaseIfMoreThanOneTenant(String authorityId);

    @Query("DELETE FROM RoleBindingData rb WHERE rb.junction.roleId=:roleId")
    int deleteRoleBindingsByRoleId(String roleId);

    @Query("DELETE FROM RoleBindingData rb WHERE rb.junction.authorityId=:authorityId")
    int deleteRoleBindingsByAuthorityId(String authorityId);
}