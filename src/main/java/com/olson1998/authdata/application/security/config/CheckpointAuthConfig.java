package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.domain.port.security.repository.CheckpointAuthenticationConverter;
import com.olson1998.authdata.domain.port.security.repository.CheckpointAuthenticationFailureHandler;
import com.olson1998.authdata.domain.port.security.repository.CheckpointProvider;
import com.olson1998.authdata.domain.service.security.CheckpointAuthenticationConvertingService;
import com.olson1998.authdata.domain.service.security.CheckpointAuthenticationFailureHandlingService;
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
}
