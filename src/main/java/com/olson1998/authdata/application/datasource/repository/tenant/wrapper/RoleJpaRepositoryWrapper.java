package com.olson1998.authdata.application.datasource.repository.tenant.wrapper;

import com.olson1998.authdata.application.datasource.entity.tenant.RoleData;
import com.olson1998.authdata.application.datasource.repository.tenant.spring.RoleJpaRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Authority;
import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
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
    public int deleteRoles(@NonNull Set<String> roleIdSet) {
        return roleJpaRepository.deleteRoleWithGivenId(roleIdSet);
    }

    @Override
    public List<Role> saveRoles(@NonNull Set<RoleDetails> roleDetails) {
        var roleDataSet = mapRoleDetails(roleDetails);
        var persistedData = roleJpaRepository.saveAll(roleDataSet);
        return mapRoleData(persistedData);
    }

    @Override
    public Role saveRole(RoleDetails roleDetails) {
        var roleData = new RoleData(roleDetails);
        return roleJpaRepository.save(roleData);
    }

    @Override
    public int updateRoleTimestamp(@NonNull Set<String> rolesIdsSet, long timestamp) {
        return roleJpaRepository.updateRoleTimestamps(rolesIdsSet, timestamp);
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
