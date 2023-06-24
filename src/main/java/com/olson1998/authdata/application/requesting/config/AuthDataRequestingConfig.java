package com.olson1998.authdata.application.requesting.config;

import com.olson1998.authdata.application.datasource.repository.tenant.wrapper.UserJpaRepositoryWrapper;
import com.olson1998.authdata.application.datasource.repository.tenant.wrapper.UserSecretByteJpaRepositoryWrapper;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthUserRequestProcessor;
import com.olson1998.authdata.domain.service.processing.request.AuthUserProcessingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthDataRequestingConfig {

    @Bean
    public AuthUserRequestProcessor authUserRequestProcessor(UserJpaRepositoryWrapper userJpaRepositoryWrapper,
                                                             UserSecretByteJpaRepositoryWrapper userSecretByteJpaRepositoryWrapper){
        return new AuthUserProcessingService(
                userJpaRepositoryWrapper,
                userSecretByteJpaRepositoryWrapper
        );
    }
}
