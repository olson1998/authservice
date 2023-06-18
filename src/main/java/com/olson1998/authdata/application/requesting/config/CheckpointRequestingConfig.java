package com.olson1998.authdata.application.requesting.config;

import com.olson1998.authdata.application.caching.repository.impl.CaffeineCheckpointCache;
import com.olson1998.authdata.domain.port.checkpoint.repository.CheckpointRepository;
import com.olson1998.authdata.domain.port.processing.request.repository.RequestContextHolder;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.service.checkpoint.CheckpointService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckpointRequestingConfig {

    @Bean
    public CheckpointRepository checkpointRepository(TenantSecretProvider tenantSecretProvider,
                                                     RequestContextHolder requestContextHolder,
                                                     CaffeineCheckpointCache checkpointCacheRepository){
        return new CheckpointService(
                tenantSecretProvider,
                requestContextHolder,
                checkpointCacheRepository
        );
    }
}
