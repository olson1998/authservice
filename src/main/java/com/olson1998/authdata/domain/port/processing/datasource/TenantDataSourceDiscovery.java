package com.olson1998.authdata.domain.port.processing.datasource;

import javax.sql.DataSource;
import java.util.Map;

public interface TenantDataSourceDiscovery {

    Map<String, DataSource> discoverTenantDataSources(String[] tenants);
}
