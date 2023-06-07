package com.olson1998.authdata.domain.port.data.repository;

import com.olson1998.authdata.domain.port.data.stereotype.Authority;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;

import java.util.List;
import java.util.Set;

public interface AuthorityDataSourceRepository {

    List<Authority> saveAuthorities(Set<AuthorityDetails> authorityDetailsSet);

    int deleteAuthorities(Set<String> authoritiesIdSet);
}
