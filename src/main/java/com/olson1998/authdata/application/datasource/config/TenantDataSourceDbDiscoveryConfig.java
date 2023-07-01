package com.olson1998.authdata.application.datasource.config;

import com.olson1998.authdata.domain.port.data.repository.TenantSqlDbPropertiesDataSourceRepository;
import com.olson1998.authdata.domain.port.processing.datasource.SqlDataSourceFactory;
import com.olson1998.authdata.domain.port.processing.datasource.TenantDataSourceDiscovery;
import com.olson1998.authdata.domain.service.processing.datasource.TenantDataSourceDiscoveryService;
import lombok.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantDataSourceDbDiscoveryConfig {

    @Bean
    @ConditionalOnMissingBean(TenantDataSourceDiscovery.class)
    public TenantDataSourceDiscovery tenantDataSourceDiscovery(@NonNull SqlDataSourceFactory sqlDataSourceFactory,
                                                               @NonNull TenantSqlDbPropertiesDataSourceRepository tenantSqlDbPropertiesDataSourceRepository){
        return new TenantDataSourceDiscoveryService(
                sqlDataSourceFactory,
                tenantSqlDbPropertiesDataSourceRepository
        );
    }
}
