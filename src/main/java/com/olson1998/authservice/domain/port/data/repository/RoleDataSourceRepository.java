package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.data.entity.RoleAuthority;

import java.util.Set;

public interface RoleDataSourceRepository {

    Set<RoleAuthority> getRolesAuthorities(Set<String> rolesIds);
}
