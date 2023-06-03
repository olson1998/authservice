package com.olson1998.authservice.domain.service.processing.request;

import com.olson1998.authservice.domain.model.processing.report.DomainAuthoritiesSavingReport;
import com.olson1998.authservice.domain.port.data.exception.NoAuthorityDetailsFoundForPersistedEntity;
import com.olson1998.authservice.domain.port.data.repository.AuthorityDataSourceRepository;
import com.olson1998.authservice.domain.port.data.stereotype.Authority;
import com.olson1998.authservice.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authservice.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authservice.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class AuthorityRequestProcessingService implements AuthorityRequestProcessor {

    private final AuthorityDataSourceRepository authorityDataSourceRepository;

    @Override
    public AuthoritySavingReport saveAuthorities(@NonNull AuthoritySavingRequest authoritySavingRequest) {
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
            throw new NoAuthorityDetailsFoundForPersistedEntity(authoritySavingRequest.getId());
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
