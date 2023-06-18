package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.application.caching.repository.impl.CaffeineCheckpointCacheRepository;
import com.olson1998.authdata.application.caching.repository.impl.CaffeineTenantSecretCacheRepository;
import com.olson1998.authdata.application.datasource.repository.global.wrapper.TenantSecretJpaRepositoryWrapper;
import com.olson1998.authdata.domain.port.checkpoint.repository.CheckpointRepository;
import com.olson1998.authdata.domain.port.security.repository.CheckpointProvider;
import com.olson1998.authdata.domain.port.security.repository.RequestContextFactory;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.repository.TokenVerifier;
import com.olson1998.authdata.domain.service.security.CheckpointProvidingService;
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
    public CheckpointProvider checkpointProvider(CheckpointRepository checkpointRepository,
                                                 CaffeineCheckpointCacheRepository checkpointCacheRepository){
        return new CheckpointProvidingService(
                checkpointRepository,
                checkpointCacheRepository
        );
    }

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
                                       CheckpointProvider checkpointProvider,
                                       CaffeineCheckpointCacheRepository checkpointCacheRepository){
        return new TokenVerifyingService(
                InetAddress.getLoopbackAddress(),
                port,
                checkpointProvider,
                requestContextFactory,
                tenantSecretProvider
        );
    }

}
