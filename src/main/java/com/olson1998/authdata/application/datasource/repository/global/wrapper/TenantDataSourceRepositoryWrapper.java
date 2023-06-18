package com.olson1998.authdata.application.datasource.repository.global.wrapper;

import com.olson1998.authdata.application.datasource.repository.global.spring.TenantDataSourceJpaRepository;
import com.olson1998.authdata.application.datasource.repository.global.spring.TenantDataSourceUserJpaRepository;
import com.olson1998.authdata.domain.port.data.repository.TenantSqlDbPropertiesDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSourceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantDataSourceRepositoryWrapper implements TenantSqlDbPropertiesDataSourceRepository {

    private final TenantDataSourceJpaRepository tenantDataSourceJpaRepository;

    private final TenantDataSourceUserJpaRepository tenantDataSourceUserJpaRepository;

    @Override
    public Optional<TenantDataSource> getTenantDataSourceByTenantId(String tid) {
        return tenantDataSourceJpaRepository.selectTenantDataSourceByTenantId(tid)
                .map(TenantDataSource.class::cast)
                .map(this::appendUsers);
    }

    @Override
    public boolean isTenantDataSourceTimestampValid(String tid, Long timestamp) {
        return tenantDataSourceJpaRepository.selectCaseIfGivenTimestampIsValid(tid, timestamp);
    }

    private TenantDataSource appendUsers(TenantDataSource tenantDataSource){
        var tid = tenantDataSource.getTid();
        var users = tenantDataSourceUserJpaRepository.selectTenantDataSourceUsersByTenantId(tid)
                .stream()
                .map(TenantDataSourceUser.class::cast)
                .toList();
        tenantDataSource.addTenantDataSourceUsers(users);
        return tenantDataSource;
    }
}
