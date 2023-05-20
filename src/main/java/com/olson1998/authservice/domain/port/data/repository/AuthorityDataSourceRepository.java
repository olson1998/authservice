package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.port.data.entity.Authority;
import com.olson1998.authservice.domain.port.request.data.AuthorityDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface AuthorityDataSourceRepository {

    @Transactional
    List<Authority> saveAuthorities(Set<AuthorityDetails> authorityDetailsSet);

    @Transactional
    int deleteAuthorities(Set<String> authoritiesIdSet);
}
