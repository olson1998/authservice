package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.AuthorityData;
import com.olson1998.authservice.application.datasource.repository.jpa.AuthorityJpaRepository;
import com.olson1998.authservice.domain.port.data.entity.RoleAuthority;
import com.olson1998.authservice.domain.port.data.repository.AuthorityDataSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorityJpaRepositoryWrapper implements AuthorityDataSourceRepository {

    private final AuthorityJpaRepository authorityJpaRepository;

    protected static RoleAuthority mapAuthority(AuthorityData authorityData){
        return authorityData;
    }
}
