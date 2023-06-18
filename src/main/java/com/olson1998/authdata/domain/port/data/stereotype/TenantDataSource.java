package com.olson1998.authdata.domain.port.data.stereotype;

import com.olson1998.authdata.domain.port.data.utils.SqlDataSourceType;

import java.util.List;
import java.util.Optional;

public interface TenantDataSource {

    Long getId();

    SqlDataSourceType getSqlDataSourceType();

    String getTid();

    Long getTimestamp();

    String getHost();

    Optional<Integer> getPort();

    String getDatabase();

    String getSchema();

    Optional<Integer> getLoginTimeout();

    List<TenantDataSourceUser> getTenantDataSourceUsers();

    void addTenantDataSourceUsers(List<TenantDataSourceUser> tenantDataSourceUsers);

    boolean isMatching(TenantDataSource tenantDataSource);
}
