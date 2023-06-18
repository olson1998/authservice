package com.olson1998.authdata.domain.port.processing.datasource;

import javax.sql.DataSource;

public interface TenantSqlDataSourceRepository {

    DataSource getForTenant(String tid);
}
