package com.olson1998.authdata.domain.port.data.repository;

import com.olson1998.authdata.domain.port.data.stereotype.Authority;
import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

public interface RoleDataSourceRepository {

    Set<Authority> getRolesAuthorities(Set<String> rolesIds);

    int deleteAllPrivateRolesByUserId(long userId);

    int deleteRoles(Set<String> roleIdSet);

    List<Role> saveRoles(Set<RoleDetails> roleDetails);

    int updateRoleTimestamp(@NonNull Set<String> rolesIdsSet, long timestamp);
}
