package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.RoleData;
import com.olson1998.authservice.application.datasource.repository.jpa.RoleJpaRepository;
import com.olson1998.authservice.domain.port.data.stereotype.Authority;
import com.olson1998.authservice.domain.port.data.stereotype.Role;
import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleJpaRepositoryWrapper implements RoleDataSourceRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Set<Authority> getRolesAuthorities(@NonNull Set<String> rolesIds) {
        return roleJpaRepository.selectRolesAuthorities(rolesIds, System.currentTimeMillis()).stream()
                .map(AuthorityJpaRepositoryWrapper::mapAuthority)
                .collect(Collectors.toSet());
    }

    @Override
    public int deleteAllPrivateRolesByUserId(long userId) {
        return roleJpaRepository.deleteAllPrivateRolesByUserId(userId);
    }

    @Override
    public List<Role> saveRoles(@NonNull Set<RoleDetails> roleDetails) {
        var roleDataSet = mapRoleDetails(roleDetails);
        var persistedData = roleJpaRepository.saveAll(roleDataSet);
        return mapRoleData(persistedData);
    }

    private Set<RoleData> mapRoleDetails(Set<RoleDetails> roleDetails){
        return roleDetails.stream()
                .map(RoleData::new)
                .collect(Collectors.toUnmodifiableSet());
    }

    private List<Role> mapRoleData(List<RoleData> roleDataList){
        return roleDataList.stream()
                .map(RoleJpaRepositoryWrapper::mapRole)
                .toList();
    }

    protected static Role mapRole(RoleData roleData){
        return roleData;
    }


}
