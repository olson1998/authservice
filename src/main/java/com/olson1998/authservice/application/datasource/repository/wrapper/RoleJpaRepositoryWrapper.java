package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.RoleData;
import com.olson1998.authservice.application.datasource.repository.jpa.RoleJpaRepository;
import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.data.entity.RoleAuthority;
import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleJpaRepositoryWrapper implements RoleDataSourceRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Set<RoleAuthority> getRolesAuthorities(Set<String> rolesIds) {
        return roleJpaRepository.selectRolesAuthorities(rolesIds, System.currentTimeMillis()).stream()
                .map(AuthorityJpaRepositoryWrapper::mapAuthority)
                .collect(Collectors.toSet());
    }

    @Override
    public int deleteAllPrivateRolesByUserId(long userId) {
        return roleJpaRepository.deleteAllPrivateRolesByUserId(userId);
    }

    protected static Role mapRole(RoleData roleData){
        return roleData;
    }


}
