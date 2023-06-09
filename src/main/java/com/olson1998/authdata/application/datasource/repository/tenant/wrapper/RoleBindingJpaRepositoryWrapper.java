package com.olson1998.authdata.application.datasource.repository.tenant.wrapper;

import com.olson1998.authdata.application.datasource.entity.tenant.RoleBindingData;
import com.olson1998.authdata.application.datasource.repository.tenant.spring.RoleBindingJpaRepository;
import com.olson1998.authdata.domain.port.data.repository.RoleBindingDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleBindingJpaRepositoryWrapper implements RoleBindingDataSourceRepository {

    private final RoleBindingJpaRepository roleBindingJpaRepository;

    @Override
    public Set<String> getRoleIdsOfBoundedAuthorities(Set<String> authoritiesIds) {
        return roleBindingJpaRepository.selectRoleIdsOfBindingAuthorities(authoritiesIds);
    }

    @Override
    public int deleteRoleBindings(@NonNull String roleId) {
        return roleBindingJpaRepository.deleteRoleBindingByRolesIdSet(roleId);
    }

    @Override
    public int deleteRoleBoundsOfAuthorities(Set<String> authoritiesIds) {
        return roleBindingJpaRepository.deleteAllBoundsOfGivenAuthorities(authoritiesIds);
    }

    @Override
    public int deleteRoleBoundsForGivenAuthority(String roleId, Set<String> authoritiesIds) {
        return roleBindingJpaRepository.deleteRoleBindingsByRoleIdAndSetOfAuthorities(roleId, authoritiesIds);
    }

    @Override
    public List<RoleBinding> saveRoleBindings(@NonNull Set<RoleBindingClaim> claims) {
        var roleBindingsData = createRoleBindingDataFromClaims(claims);
        var savedRoleBindings = roleBindingJpaRepository.saveAll(roleBindingsData);
        return savedRoleBindings.stream()
                .map(this::mapRoleBinding)
                .toList();
    }

    private Set<RoleBindingData> createRoleBindingDataFromClaims(Set<RoleBindingClaim> claims){
        return claims.stream()
                .map(RoleBindingData::new)
                .collect(Collectors.toUnmodifiableSet());
    }

    private RoleBinding mapRoleBinding(RoleBindingData roleBindingData){
        return roleBindingData;
    }
}
