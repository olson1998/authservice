package com.olson1998.authdata.application.processing.config.datasource;

import com.olson1998.authdata.application.caching.repository.impl.TenantDataSourceCaffeineCache;
import com.olson1998.authdata.application.datasource.repository.global.wrapper.TenantDataSourceRepositoryWrapper;
import com.olson1998.authdata.domain.port.processing.datasource.SqlDataSourceFactory;
import com.olson1998.authdata.domain.port.processing.datasource.TenantSqlDataSourceRepository;
import com.olson1998.authdata.domain.service.processing.datasource.TenantSqlDataSourceFactory;
import com.olson1998.authdata.domain.service.processing.datasource.TenantSqlDataSourceService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantDatasourceConfig {

    @Bean
    public SqlDataSourceFactory sqlDataSourceFactory(){
        return new TenantSqlDataSourceFactory();
    }

    @Bean
    public TenantSqlDataSourceRepository tenantSqlDataSourceRepository(@NonNull SqlDataSourceFactory sqlDataSourceFactory,
                                                                       @NonNull TenantDataSourceCaffeineCache tenantDataSourceCaffeineCache,
                                                                       @NonNull TenantDataSourceRepositoryWrapper tenantDataSourceRepositoryWrapper){
        return new TenantSqlDataSourceService(
                sqlDataSourceFactory,
                tenantDataSourceCaffeineCache,
                tenantDataSourceRepositoryWrapper
        );
    }
}
