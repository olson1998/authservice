package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.AuthorityData;
import com.olson1998.authservice.application.datasource.repository.jpa.AuthorityJpaRepository;
import com.olson1998.authservice.domain.port.data.stereotype.Authority;
import com.olson1998.authservice.domain.port.data.repository.AuthorityDataSourceRepository;
import com.olson1998.authservice.domain.port.request.stereotype.data.AuthorityDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorityJpaRepositoryWrapper implements AuthorityDataSourceRepository {

    private final AuthorityJpaRepository authorityJpaRepository;

    @Override
    public List<Authority> saveAuthorities(@NonNull Set<AuthorityDetails> authorityDetailsSet) {
        var dataSet = mapAuthorityDetailsToDataSet(authorityDetailsSet);
        var persistedData = authorityJpaRepository.saveAll(dataSet);
        return mapAuthorityDataList(persistedData);
    }

    @Override
    public int deleteAuthorities(Set<String> authoritiesIdSet) {
        return authorityJpaRepository.deleteAuthorityFromIdSet(authoritiesIdSet);
    }

    private Set<AuthorityData> mapAuthorityDetailsToDataSet(@NonNull Set<AuthorityDetails> authorityDetailsSet){
        return authorityDetailsSet.stream()
                .map(AuthorityData::new)
                .collect(Collectors.toUnmodifiableSet());
    }

    private List<Authority> mapAuthorityDataList(@NonNull List<AuthorityData> authorityDataList){
        return authorityDataList.stream()
                .map(AuthorityJpaRepositoryWrapper::mapAuthority)
                .toList();
    }

    protected static Authority mapAuthority(AuthorityData authorityData){
        return authorityData;
    }
}
