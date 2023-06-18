package com.olson1998.authdata.domain.port.data.stereotype;

import com.olson1998.authdata.domain.port.data.utils.SqlDataSourceType;

import java.util.List;

public interface TenantDataSource {

    Long getId();

    SqlDataSourceType getSqlDataSourceType();

    String getTid();

    String getHost();

    Integer getPort();

    String getDatabase();

    String getSchema();

    List<TenantDataSourceUser> getTenantDataSourceUsers();

    void addTenantDataSourceUsers(List<TenantDataSourceUser> tenantDataSourceUsers);

    boolean isMatching(TenantDataSource tenantDataSource);
}
