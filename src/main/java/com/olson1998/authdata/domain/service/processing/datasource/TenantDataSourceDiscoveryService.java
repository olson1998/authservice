package com.olson1998.authdata.domain.service.processing.datasource;

import com.olson1998.authdata.domain.port.data.repository.TenantSqlDbPropertiesDataSourceRepository;
import com.olson1998.authdata.domain.port.processing.datasource.SqlDataSourceFactory;
import com.olson1998.authdata.domain.port.processing.datasource.TenantDataSourceDiscovery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j

@RequiredArgsConstructor
public class TenantDataSourceDiscoveryService implements TenantDataSourceDiscovery {

    private final SqlDataSourceFactory sqlDataSourceFactory;

    private final TenantSqlDbPropertiesDataSourceRepository tenantSqlDbPropertiesDataSourceRepository;

    @Override
    public Map<String, DataSource> discoverTenantDataSources(String [] tenants) {
        var tenantDataSourcesMap = new HashMap<String, DataSource>();
        log.info("Running tenant data source discovery for tenants: {}", Arrays.toString(tenants));
        tenantSqlDbPropertiesDataSourceRepository.getTenantsDataSources(tenants).forEach(tenantDataSource -> {
            var ds = sqlDataSourceFactory.fabricate(tenantDataSource);
            tenantDataSourcesMap.put(tenantDataSource.getTid(), ds);
        });
        return tenantDataSourcesMap;
    }

}
