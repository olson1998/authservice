package com.olson1998.authdata.application.datasource.config;

import com.olson1998.authdata.application.developer.utils.SampleDataInject;
import com.olson1998.authdata.domain.port.data.repository.TenantSqlDbPropertiesDataSourceRepository;
import com.olson1998.authdata.domain.port.processing.datasource.SqlDataSourceFactory;
import com.olson1998.authdata.domain.port.processing.datasource.TenantDataSourceDiscovery;
import com.olson1998.authdata.domain.service.processing.datasource.TenantDataSourceDiscoveryService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("developer")

@Configuration
public class DeveloperDataSourceDiscoveryConfig {

    @Bean
    public TenantDataSourceDiscovery tenantDataSourceDiscovery(@NonNull SampleDataInject sampleDataInject,
                                                               @NonNull SqlDataSourceFactory sqlDataSourceFactory,
                                                               @NonNull TenantSqlDbPropertiesDataSourceRepository tenantSqlDbPropertiesDataSourceRepository){
        //sampleDataInject.injectTestTenant();
        return new TenantDataSourceDiscoveryService(
                sqlDataSourceFactory,
                tenantSqlDbPropertiesDataSourceRepository
        );
    }
}
