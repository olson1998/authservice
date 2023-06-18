package com.olson1998.authdata.domain.port.processing.datasource;

import com.olson1998.authdata.domain.port.data.stereotype.TenantDataSource;

import javax.sql.DataSource;

public interface SqlDataSourceFactory {

    DataSource fabricate(TenantDataSource tenantDataSource);
}
