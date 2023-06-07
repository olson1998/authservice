package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleBindingClaim;

import java.util.List;
import java.util.Set;

public interface RoleBindingDataSourceRepository {

    /**
     * Checks if there is more than one tenant of given authority
     * @param authorityId authority id
     * @return yes if there is only one tenant, false if more
     */
    boolean areAnyOtherAuthorityTenants(String authorityId);

    /**
     * Deletes all authority bindings from table
     * @param roleId Role's id
     * @return number of deleted rows
     */
    int deleteRoleAuthorityBindingsByRoleId(String roleId);

    int deleteRoleAuthorityBindingsByAuthorityId(String authorityId);

    int deleteRoleBindings(Set<String> rolesIdSet);

    /**
     * Inserts role binding into table
     * @param claims set of claims containg binding data
     */
    List<RoleBinding> saveRoleBindings(Set<RoleBindingClaim> claims);
}
