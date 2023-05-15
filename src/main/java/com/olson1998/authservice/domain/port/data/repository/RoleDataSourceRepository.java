package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.data.entity.RoleAuthority;
import com.olson1998.authservice.domain.port.request.data.RoleDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

public interface RoleDataSourceRepository {

    Set<RoleAuthority> getRolesAuthorities(Set<String> rolesIds);

    @Transactional
    int deleteAllPrivateRolesByUserId(long userId);

    @Transactional
    Collection<Role> saveRoles(Set<RoleDetails> roleDetails);

}
