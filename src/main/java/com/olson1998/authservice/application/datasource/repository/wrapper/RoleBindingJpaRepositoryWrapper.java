package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.RoleBindingData;
import com.olson1998.authservice.application.datasource.repository.jpa.RoleBindingJpaRepository;
import com.olson1998.authservice.domain.port.data.repository.RoleBindingDataSourceRepository;
import com.olson1998.authservice.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleBindingJpaRepositoryWrapper implements RoleBindingDataSourceRepository {

    private final RoleBindingJpaRepository roleBindingJpaRepository;

    @Override
    public boolean areAnyOtherAuthorityTenants(@NonNull String authorityId) {
        return roleBindingJpaRepository.selectCaseIfMoreThanOneTenant(authorityId);
    }

    @Override
    @Transactional
    public int deleteRoleAuthorityBindingsByRoleId(@NonNull String roleId) {
        return roleBindingJpaRepository.deleteRoleBindingsByRoleId(roleId);
    }

    @Override
    @Transactional
    public int deleteRoleAuthorityBindingsByAuthorityId(@NonNull String authorityId) {
        return roleBindingJpaRepository.deleteRoleBindingsByAuthorityId(authorityId);
    }

    @Override
    @Transactional
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
