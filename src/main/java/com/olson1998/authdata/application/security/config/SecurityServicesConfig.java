package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.application.caching.repository.impl.CaffeineCheckpointCacheRepository;
import com.olson1998.authdata.application.caching.repository.impl.CaffeineTenantSecretCacheRepository;
import com.olson1998.authdata.application.datasource.repository.wrapper.TenantSecretJpaRepositoryWrapper;
import com.olson1998.authdata.domain.port.security.RequestContextFactory;
import com.olson1998.authdata.domain.port.security.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.TokenVerifier;
import com.olson1998.authdata.domain.service.security.RequestContextFabricationService;
import com.olson1998.authdata.domain.service.security.TenantSecretProvidingService;
import com.olson1998.authdata.domain.service.security.TokenVerifyingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;

@Configuration
public class SecurityServicesConfig {

    @Value("${server.port}")
    private int port;

    @Bean
    public RequestContextFactory requestContextFactory(){
        return new RequestContextFabricationService();
    }

    @Bean
    public TenantSecretProvider tenantSecretProvider(CaffeineTenantSecretCacheRepository caffeineTenantSecretCacheRepository,
                                                     TenantSecretJpaRepositoryWrapper tenantSecretJpaRepositoryWrapper){
        return new TenantSecretProvidingService(
                caffeineTenantSecretCacheRepository,
                tenantSecretJpaRepositoryWrapper
        );
    }

    @Bean
    public TokenVerifier tokenVerifier(RequestContextFactory requestContextFactory,
                                       TenantSecretProvider tenantSecretProvider,
                                       CaffeineCheckpointCacheRepository checkpointCacheRepository){
        return new TokenVerifyingService(
                InetAddress.getLoopbackAddress(),
                port,
                requestContextFactory,
                tenantSecretProvider,
                checkpointCacheRepository
        );
    }

}
