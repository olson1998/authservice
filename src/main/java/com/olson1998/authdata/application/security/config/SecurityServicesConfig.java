package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.application.caching.repository.impl.TenantSecretCaffeineCache;
import com.olson1998.authdata.application.datasource.repository.global.wrapper.TenantSecretJpaRepositoryWrapper;
import com.olson1998.authdata.domain.port.security.repository.RequestContextFactory;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.service.security.RequestContextFabricationService;
import com.olson1998.authdata.domain.service.security.TenantSecretProvidingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityServicesConfig {

    @Bean
    public RequestContextFactory requestContextFactory(){
        return new RequestContextFabricationService();
    }

    @Bean
    public TenantSecretProvider tenantSecretProvider(TenantSecretCaffeineCache tenantSecretCaffeineCache,
                                                     TenantSecretJpaRepositoryWrapper tenantSecretJpaRepositoryWrapper){
        return new TenantSecretProvidingService(
                tenantSecretCaffeineCache,
                tenantSecretJpaRepositoryWrapper
        );
    }


}
