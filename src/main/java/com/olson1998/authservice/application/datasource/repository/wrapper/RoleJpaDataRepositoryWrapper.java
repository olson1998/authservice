package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.RoleData;
import com.olson1998.authservice.application.datasource.repository.jpa.RoleJpaRepository;
import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.data.entity.RoleAuthority;
import com.olson1998.authservice.domain.port.data.repository.RoleDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleJpaDataRepositoryWrapper implements RoleDataRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Set<RoleAuthority> getRolesAuthorities(Set<String> rolesIds) {
        return roleJpaRepository.selectRolesAuthorities(rolesIds, System.currentTimeMillis()).stream()
                .map(AuthorityJpaDataRepositoryWrapper::mapAuthority)
                .collect(Collectors.toSet());
    }

    protected static Role mapRole(RoleData roleData){
        return roleData;
    }


}
