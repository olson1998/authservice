package com.olson1998.authdata.domain.port.data.repository;

import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;

import java.util.List;
import java.util.Optional;

public interface TenantSqlDbPropertiesDataSourceRepository {

    List<TenantDataSource> getTenantsDataSources(String ... tenants);

    Optional<TenantDataSource> getTenantDataSourceByTenantId(String tid);

    boolean isTenantDataSourceTimestampValid(String tid, Long timestamp);
}
