package com.olson1998.authdata.application.datasource.repository.global.wrapper;

import com.olson1998.authdata.application.datasource.entity.global.TenantDataSourceData;
import com.olson1998.authdata.application.datasource.entity.global.TenantDataSourceUserData;
import com.olson1998.authdata.application.datasource.repository.global.spring.TenantDataSourceJpaRepository;
import com.olson1998.authdata.application.datasource.repository.global.spring.TenantDataSourceUserJpaRepository;
import com.olson1998.authdata.domain.port.data.repository.TenantSqlDbPropertiesDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;
import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSourceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantDataSourceRepositoryWrapper implements TenantSqlDbPropertiesDataSourceRepository {

    private final TenantDataSourceJpaRepository tenantDataSourceJpaRepository;

    private final TenantDataSourceUserJpaRepository tenantDataSourceUserJpaRepository;

    @Override
    public List<TenantDataSource> getTenantsDataSources(String... tenants) {
        var users = tenantDataSourceUserJpaRepository.selectTenantDataSourceUsersOfTenants(tenants);
        var dataSourcesData = tenantDataSourceJpaRepository.selectTenantsDataSources(tenants);
        return this.mapDataSourceUsers(dataSourcesData, users);
    }

    @Override
    public Optional<TenantDataSource> getTenantDataSourceByTenantId(String tid) {
        return getTenantsDataSources(new String[]{tid}).stream()
                .findFirst();
    }

    @Override
    public boolean isTenantDataSourceTimestampValid(String tid, Long timestamp) {
        return tenantDataSourceJpaRepository.selectCaseIfGivenTimestampIsValid(tid, timestamp);
    }

    private List<TenantDataSource> mapDataSourceUsers(List<TenantDataSourceData> tenantDataSources, List<TenantDataSourceUserData> tenantDataSourceUserData){
        tenantDataSources.forEach(tenantDataSource -> {
            var dsid = tenantDataSource.getId();
            var users = castTenantDataSourceData(tenantDataSourceUserData).stream()
                    .filter(tenantDataSourceUser -> tenantDataSourceUser.getDataSourceId().equals(dsid))
                    .toList();
            tenantDataSource.addTenantDataSourceUsers(users);
        });
        return tenantDataSources.stream()
                .map(TenantDataSource.class::cast)
                .toList();
    }

    private List<TenantDataSourceUser> castTenantDataSourceData(List<TenantDataSourceUserData> tenantDataSourceUserData){
        return tenantDataSourceUserData.stream()
                .map(TenantDataSourceUser.class::cast)
                .toList();
    }
}
