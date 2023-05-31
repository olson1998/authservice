package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.data.stereotype.Authority;
import com.olson1998.authservice.domain.port.data.stereotype.Role;
import com.olson1998.authservice.domain.port.request.stereotype.data.RoleDetails;

import java.util.List;
import java.util.Set;

public interface RoleDataSourceRepository {

    Set<Authority> getRolesAuthorities(Set<String> rolesIds);

    int deleteAllPrivateRolesByUserId(long userId);

    List<Role> saveRoles(Set<RoleDetails> roleDetails);

}
