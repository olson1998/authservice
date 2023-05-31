package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.data.stereotype.Authority;
import com.olson1998.authservice.domain.port.request.stereotype.data.AuthorityDetails;

import java.util.List;
import java.util.Set;

public interface AuthorityDataSourceRepository {

    List<Authority> saveAuthorities(Set<AuthorityDetails> authorityDetailsSet);

    int deleteAuthorities(Set<String> authoritiesIdSet);
}
