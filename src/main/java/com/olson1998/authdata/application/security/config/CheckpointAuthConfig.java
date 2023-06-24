package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.domain.port.security.repository.*;
import com.olson1998.authdata.domain.service.security.CheckpointAuthenticationConvertingService;
import com.olson1998.authdata.domain.service.security.CheckpointAuthenticationFailureHandlingService;
import com.olson1998.authdata.domain.service.security.CheckpointVerificationService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckpointAuthConfig {

    @Bean
    public CheckpointAuthenticationConverter checkpointAuthenticationConverter(@NonNull CheckpointProvider checkpointProvider){
        return new CheckpointAuthenticationConvertingService(checkpointProvider);
    }

    @Bean
    public CheckpointAuthenticationFailureHandler checkpointAuthenticationFailureHandler(){
        return new CheckpointAuthenticationFailureHandlingService();
    }

    @Bean
    public CheckpointVerifier checkpointVerifier(@NonNull TenantSecretProvider tenantSecretProvider){
        return new CheckpointVerificationService(tenantSecretProvider);
    }
}
