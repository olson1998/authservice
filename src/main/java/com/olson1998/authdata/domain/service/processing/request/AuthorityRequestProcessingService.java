package com.olson1998.authdata.domain.service.processing.request;

import com.olson1998.authdata.domain.model.exception.data.DifferentUpdatedEntitiesTimestampsException;
import com.olson1998.authdata.domain.model.processing.report.DomainAuthoritiesSavingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainAuthorityDeletingReport;
import com.olson1998.authdata.domain.model.processing.request.LinkedRoleBoundsDeletingRequest;
import com.olson1998.authdata.domain.model.processing.request.payload.LinkedRoleBoundsDeletingClaim;
import com.olson1998.authdata.domain.port.data.exception.NoAuthorityDetailsFoundForPersistedEntity;
import com.olson1998.authdata.domain.port.data.repository.AuthorityDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Authority;
import com.olson1998.authdata.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthorityDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBoundsDeletingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthorityDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBoundDeletingClaim;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.DELETE;
import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.SAVE;

@Slf4j
@RequiredArgsConstructor
public class AuthorityRequestProcessingService implements AuthorityRequestProcessor {

    private final AuthorityDataSourceRepository authorityDataSourceRepository;

    private final Function<RoleBoundDeletingRequest, RoleBoundsDeletingReport> roleBoundDeletingRequestFunction;

    private final Function<Set<String>, Set<RoleBinding>> roleBindingsByAuthoritiesIdsProvidingFunction;

    private final BiFunction<Set<String>, Long, Integer> roleTimestampUpdatingFunction;

    @Override
    public AuthoritySavingReport saveAuthorities(@NonNull AuthoritySavingRequest authoritySavingRequest) {
        ProcessingRequestLogger.log(log, authoritySavingRequest, SAVE, Authority.class);
        var authoritiesDetails = authoritySavingRequest.getAuthoritiesDetails();
        var persistedAuthorities = authorityDataSourceRepository.saveAuthorities(authoritiesDetails);
        var persistedAuthoritiesMap = createPersistedAuthoritiesMap(
                authoritySavingRequest,
                persistedAuthorities
        );
        return new DomainAuthoritiesSavingReport(
                authoritySavingRequest.getId(),
                persistedAuthoritiesMap
        );
    }

    @Override
    public AuthorityDeletingReport deleteAuthorities(AuthorityDeletingRequest authorityDeletingRequest) {
        ProcessingRequestLogger.log(log, authorityDeletingRequest, DELETE, Authority.class);
        var authoritiesIds = authorityDeletingRequest.getAuthoritiesIds();
        var roleBounds = roleBindingsByAuthoritiesIdsProvidingFunction.apply(authoritiesIds);
        var boundedRolesIds = roleBounds.stream()
                .map(RoleBinding::getRoleId)
                .collect(Collectors.toUnmodifiableSet());

        var linkedRoleBoundsDeletingClaimMap = mapRoleBindings(boundedRolesIds, roleBounds);

        var roleBoundDeletingReq = new LinkedRoleBoundsDeletingRequest(
                authorityDeletingRequest.getId(),
                linkedRoleBoundsDeletingClaimMap
        );
        var deletedBounds = roleBoundDeletingRequestFunction.apply(roleBoundDeletingReq).getDeletedRolesBoundsQty();
        var deletedAuthorities = authorityDataSourceRepository.deleteAuthorities(authorityDeletingRequest.getAuthoritiesIds());
        if(deletedAuthorities != authoritiesIds.size()){
            throw new DifferentUpdatedEntitiesTimestampsException(
                    log,
                    authorityDeletingRequest.getId(),
                    authoritiesIds.size(),
                    deletedAuthorities
            );
        }
        var updatedRoles = roleTimestampUpdatingFunction.apply(boundedRolesIds, System.currentTimeMillis());

        if(updatedRoles != boundedRolesIds.size()){
            throw new DifferentUpdatedEntitiesTimestampsException(
                    log,
                    authorityDeletingRequest.getId(),
                    boundedRolesIds.size(),
                    updatedRoles
            );
        }
        return new DomainAuthorityDeletingReport(
                authorityDeletingRequest.getId(),
                deletedAuthorities,
                deletedBounds,
                boundedRolesIds
        );
    }

    private Map<String, AuthorityDetails> createPersistedAuthoritiesMap(@NonNull AuthoritySavingRequest authoritySavingRequest,
                                                                        @NonNull List<Authority> persistedAuthorityList){
        var persistedAuthoritiesMap = new HashMap<String, AuthorityDetails>();
        persistedAuthorityList.forEach(authority -> {
            var authorityDetails = getMatchingAuthoritiesDetails(authoritySavingRequest, authority);
            var id = authority.getId();
            persistedAuthoritiesMap.put(id, authorityDetails);
        });
        return persistedAuthoritiesMap;
    }

    private AuthorityDetails getMatchingAuthoritiesDetails(@NonNull AuthoritySavingRequest authoritySavingRequest, Authority authority){
        var authoritiesDetailsSet = authoritySavingRequest.getAuthoritiesDetails();
        try{
            return authoritiesDetailsSet.stream()
                    .filter(details -> details.isMatching(authority))
                    .findFirst()
                    .orElseThrow();
        }catch (NoSuchElementException e){
            throw new NoAuthorityDetailsFoundForPersistedEntity(log, authoritySavingRequest.getId());
        }
    }

    private Map<String, RoleBoundDeletingClaim> mapRoleBindings(Set<String> rolesIds, Set<RoleBinding> roleBindings){
        var roleBoundsDeletingReqMap = new HashMap<String, RoleBoundDeletingClaim>();
        rolesIds.forEach(roleId ->{
                    var authoritiesIds = roleBindings.stream()
                            .filter(roleBinding -> roleBinding.getRoleId().equals(roleId))
                            .map(RoleBinding::getAuthorityId)
                            .collect(Collectors.toUnmodifiableSet());
                    var roleBoundDeletingClaim = new LinkedRoleBoundsDeletingClaim(false, authoritiesIds);
                    roleBoundsDeletingReqMap.put(roleId, roleBoundDeletingClaim);
                });
        return roleBoundsDeletingReqMap;
    }
}
