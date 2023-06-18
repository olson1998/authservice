package com.olson1998.authdata.domain.port.data.repository;

import com.olson1998.authdata.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;

import java.util.List;
import java.util.Set;

public interface RoleBindingDataSourceRepository {

    Set<RoleBinding> getRoleBindingsByAuthoritiesIds(Set<String> authoritiesIds);

    int deleteRoleBindings(String roleId);

    int deleteRoleBoundsForGivenAuthority(String roleId, Set<String> authoritiesIds);

    /**
     * Inserts role binding into table
     * @param claims set of claims containg binding data
     */
    List<RoleBinding> saveRoleBindings(Set<RoleBindingClaim> claims);
}
