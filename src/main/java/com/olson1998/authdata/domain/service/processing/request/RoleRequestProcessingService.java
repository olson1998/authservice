package com.olson1998.authdata.domain.service.processing.request;

import com.olson1998.authdata.domain.model.processing.report.DomainRoleBindingDeletingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainRoleBindingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainRoleDeletingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainRoleSavingReport;
import com.olson1998.authdata.domain.model.processing.request.LinkedAuthoritySavingRequest;
import com.olson1998.authdata.domain.model.processing.request.LinkedRoleBindingClaim;
import com.olson1998.authdata.domain.model.processing.request.LinkedRoleBoundsDeletingRequest;
import com.olson1998.authdata.domain.port.data.exception.DeletedRolesQtyDoesntMuchRequestedQty;
import com.olson1998.authdata.domain.port.data.repository.RoleBindingDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authdata.domain.port.processing.report.stereotype.*;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.*;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBoundDeletingClaim;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.DELETE;
import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.SAVE;

@Slf4j
@RequiredArgsConstructor
public class RoleRequestProcessingService implements RoleRequestProcessor {

    private final AuthorityRequestProcessor authorityRequestProcessor;

    private final RoleDataSourceRepository roleDataSourceRepository;

    private final RoleBindingDataSourceRepository roleBindingDataSourceRepository;

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
    public RoleBindingReport saveNewRoleBounds(@NonNull RoleBoundSavingRequest request) {
        ProcessingRequestLogger.log(log, request, SAVE, RoleBinding.class);
        var roleBindingClaims = new HashSet<>(request.getRolesBindingsClaims());
        Map<String, AuthorityDetails> savedAuthorityDetails = null;
        if(request.getRoleIdAuthoritySavingRequestMap().size() > 0){
            var linkedRequest = new LinkedAuthoritySavingRequest(request);
            var report = authorityRequestProcessor.saveAuthorities(linkedRequest);
            savedAuthorityDetails = report.getPersistedAuthoritiesDetailsMap();
            var roleBindingsClaims = createLinkedRoleBindingClaimsSet(request, report);
            roleBindingsClaims.addAll(roleBindingClaims);
        }
        var savedRoleBindingsEntries = roleBindingDataSourceRepository.saveRoleBindings(roleBindingClaims);
        var savedRoleBindingsMap = new HashMap<String, Set<String>>();
        savedRoleBindingsEntries.forEach(roleBinding -> {
            var roleId = roleBinding.getRoleId();
            var authId= roleBinding.getAuthorityId();
            if(savedRoleBindingsMap.containsKey(roleId)){
                savedRoleBindingsMap.get(roleId).add(authId);
            }else {
                var authSet = new HashSet<String>();
                authSet.add(authId);
                savedRoleBindingsMap.put(roleId, authSet);
            }
        });
        return new DomainRoleBindingReport(
                request.getId(),
                savedRoleBindingsMap,
                savedAuthorityDetails
        );
    }

    @Override
    public RoleDeletingReport deleteRoles(@NonNull RoleDeletingRequest request) {
        var rolesIds = request.getRoleIdSet();
        var roleBoundsDelReq = new LinkedRoleBoundsDeletingRequest(request);
        var linkedReport = deleteRoleBounds(roleBoundsDelReq);
        ProcessingRequestLogger.log(log, request, DELETE, Role.class);
        var deleted = roleDataSourceRepository.deleteRoles(rolesIds);
        if(deleted != rolesIds.size()){
            throw new DeletedRolesQtyDoesntMuchRequestedQty(rolesIds.size(), deleted, request.getId(), log);
        }
        return new DomainRoleDeletingReport(
                request.getId(),
                deleted,
                linkedReport.getDeletedRolesBoundsQty()
        );
    }

    @Override
    public RoleBoundsDeletingReport deleteRoleBounds(@NonNull RoleBoundDeletingRequest request) {
        ProcessingRequestLogger.log(log, request, DELETE, RoleBinding.class);
        var roleDeletedBoundsQty = new HashMap<String, Integer>();
        request.getRoleBoundsMap().forEach((roleId, claim) ->{
            var deleted = deleteRoleBound(roleId, claim);
            roleDeletedBoundsQty.put(roleId, deleted);
        });
        return new DomainRoleBindingDeletingReport(
                request.getId(),
                roleDeletedBoundsQty
        );
    }

    @Override
    public int deleteUserRoles(@NonNull UserDeletingRequest request) {
        ProcessingRequestLogger.log(log, request, DELETE, Role.class);
        var userId = request.getUserId();
        return roleDataSourceRepository.deleteAllPrivateRolesByUserId(userId);
    }

    private int deleteRoleBound(String roleId, RoleBoundDeletingClaim claim){
        if(claim.isDeleteAll()){
            return roleBindingDataSourceRepository.deleteRoleBindings(roleId);
        }else {
            return roleBindingDataSourceRepository.deleteRoleBoundsForGivenAuthority(roleId, claim.getAuthoritiesIds());
        }
    }

    private Set<RoleBindingClaim> createLinkedRoleBindingClaimsSet(RoleBoundSavingRequest request, AuthoritySavingReport report){
        var roleBindingsClaims = new HashSet<RoleBindingClaim>();
        request.getRoleIdAuthoritySavingRequestMap().forEach((roleId, requestedAuthoritiesDetails) ->{
            requestedAuthoritiesDetails.forEach(authorityDetail -> {
                report.getPersistedAuthoritiesDetailsMap().forEach((authorityId, savedAuthorityDetails) ->{
                    if(savedAuthorityDetails.equals(authorityDetail)){
                        roleBindingsClaims.add(new LinkedRoleBindingClaim(roleId, authorityId));
                    }
                });
            });
        });
        return roleBindingsClaims;
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
