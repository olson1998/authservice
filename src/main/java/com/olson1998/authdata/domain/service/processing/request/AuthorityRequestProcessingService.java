package com.olson1998.authdata.domain.service.processing.request;

import com.olson1998.authdata.domain.model.exception.data.DifferentUpdatedEntitiesTimestampsException;
import com.olson1998.authdata.domain.model.processing.report.DomainAuthoritiesSavingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainAuthorityDeletingReport;
import com.olson1998.authdata.domain.port.data.exception.NoAuthorityDetailsFoundForPersistedEntity;
import com.olson1998.authdata.domain.port.data.repository.AuthorityDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.RoleBindingDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Authority;
import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthorityDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthorityDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.DELETE;
import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.SAVE;

@Slf4j
@RequiredArgsConstructor
public class AuthorityRequestProcessingService implements AuthorityRequestProcessor {

    private final AuthorityDataSourceRepository authorityDataSourceRepository;

    private final RoleBindingDataSourceRepository roleBindingDataSourceRepository;

    private final RoleDataSourceRepository roleDataSourceRepository;

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
        ProcessingRequestLogger.log(log, authorityDeletingRequest, DELETE, Role.class);
        var authoritiesIds = authorityDeletingRequest.getAuthoritiesIds();
        var boundedRolesIds = roleBindingDataSourceRepository.getRoleIdsOfBoundedAuthorities(authoritiesIds);
        var deletedBounds = roleBindingDataSourceRepository.deleteRoleBoundsOfAuthorities(authoritiesIds);
        var deletedAuthorities = authorityDataSourceRepository.deleteAuthorities(authorityDeletingRequest.getAuthoritiesIds());
        if(deletedAuthorities != authoritiesIds.size()){
            throw new DifferentUpdatedEntitiesTimestampsException(
                    log,
                    authorityDeletingRequest.getId(),
                    authoritiesIds.size(),
                    deletedAuthorities
            );
        }
        var updatedRoles = roleDataSourceRepository.updateRoleTimestamp(boundedRolesIds, System.currentTimeMillis());
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
        var authoritiesDetails = authoritySavingRequest.getAuthoritiesDetails();
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
                    .filter(details -> isMatchingAuthorityDetails(details, authority))
                    .findFirst()
                    .orElseThrow();
        }catch (NoSuchElementException e){
            throw new NoAuthorityDetailsFoundForPersistedEntity(log, authoritySavingRequest.getId());
        }
    }

    private boolean isMatchingAuthorityDetails(AuthorityDetails authorityDetails, Authority authority){
        boolean sameLvl = false;
        boolean sameExpTime = false;
        boolean sameName = authorityDetails.getName().equals(authority.getAuthorityName());
        if(authorityDetails.getLevel() != null && authority.getLevel() != null){
            sameLvl = authorityDetails.getLevel().equals(authority.getLevel());
        }else if(authorityDetails.getLevel() == null && authority.getLevel() == null){
            sameLvl = true;
        }
        if(authorityDetails.getExpiringTime() != null && authority.getExpiringTime() != null){
            sameExpTime = authorityDetails.getExpiringTime().equals(authority.getExpiringTime());
        } else if (authorityDetails.getExpiringTime() == null && authority.getExpiringTime() == null) {
            sameExpTime = true;
        }
        return sameName && sameLvl && sameExpTime;
    }
}
