package com.olson1998.authservice.domain.service.processing.request;

import com.olson1998.authservice.domain.model.processing.report.DomainRoleSavingReport;
import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authservice.domain.port.data.stereotype.Role;
import com.olson1998.authservice.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authservice.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authservice.domain.port.processing.request.stereotype.RoleSavingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.olson1998.authservice.domain.service.processing.request.ProcessingRequestLogger.RequestType.SAVE;

@Slf4j
@RequiredArgsConstructor
public class RoleRequestProcessingService implements RoleRequestProcessor {

    private final RoleDataSourceRepository roleDataSourceRepository;

    @Override
    public RoleSavingReport saveNewRoles(@NonNull RoleSavingRequest request) {
        ProcessingRequestLogger.log(log, request, SAVE, Role.class);
        var persistedRoles= roleDataSourceRepository.saveRoles(request.getDetails());
        var persistedRolesIdMap = createPersistedRolesDetailsMap(
                request,
                persistedRoles
        );
        return new DomainRoleSavingReport(
                request.getId(),
                persistedRolesIdMap
        );
    }

    @Override
    public int deleteUserPrivateRoles(long userId) {
        roleDataSourceRepository.deleteAllPrivateRolesByUserId(userId);
        return 0;
    }

    private Map<String, RoleDetails> createPersistedRolesDetailsMap(RoleSavingRequest request,
                                                                    List<Role> persistedRoles){
        var persistedRolesDetailsMap = new HashMap<String, RoleDetails>();
        persistedRoles.forEach(role -> {
            var roleDetails = findMatchingRoleDetails(request, role);
            var id = role.getId();
            persistedRolesDetailsMap.put(id, roleDetails);
        });
        return persistedRolesDetailsMap;
    }

    private RoleDetails findMatchingRoleDetails(RoleSavingRequest request,
                                                Role role){
        return request.getDetails().stream()
                .filter(roleDetails -> match(role, roleDetails))
                .findFirst()
                .orElseThrow();
    }

    private boolean match(Role role, RoleDetails roleDetails){
        var matchingUserId = false;
        var matchingCono = false;
        var matchingRegionId = false;
        var matchingGroupId = false;
        var matchingTeamId = false;
        if(role.getUserId() != null && roleDetails.getUserId() != null){
            matchingUserId = role.getUserId().equals(roleDetails.getUserId());
        }else {
            matchingUserId = true;
        }
        if(role.getCompanyNumber() != null && roleDetails.getCompanyNumber() != null){
            matchingCono = role.getCompanyNumber().equals(roleDetails.getCompanyNumber());
        }else {
            matchingCono = true;
        }
        if(role.getRegionId() != null && roleDetails.getRegionId() != null){
            matchingRegionId = role.getRegionId().equals(roleDetails.getRegionId());
        }else {
            matchingRegionId = true;
        }
        if(role.getGroupId() != null && roleDetails.getGroupId() != null){
            matchingGroupId = role.getGroupId().equals(roleDetails.getGroupId());
        }else {
            matchingGroupId = true;
        }
        if(role.getTeamId() != null && roleDetails.getTeamId() != null){
            matchingTeamId = role.getTeamId().equals(roleDetails.getTeamId());
        }else {
            matchingTeamId = true;
        }return matchingUserId &&
                matchingCono &&
                matchingGroupId &&
                matchingRegionId &&
                matchingTeamId &&
                role.getName().equals(roleDetails.getName()) &&
                role.getSubject().equals(roleDetails.getSubject());
    }
}
