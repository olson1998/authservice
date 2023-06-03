package com.olson1998.authservice.application.processing.config;

import com.olson1998.authservice.application.datasource.repository.wrapper.RoleJpaRepositoryWrapper;
import com.olson1998.authservice.application.datasource.repository.wrapper.UserJpaRepositoryWrapper;
import com.olson1998.authservice.application.datasource.repository.wrapper.UserMembershipJpaRepositoryWrapper;
import com.olson1998.authservice.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authservice.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authservice.domain.service.processing.request.RoleRequestProcessingService;
import com.olson1998.authservice.domain.service.processing.request.UserRequestProcessingService;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
public class EntityProcessingServicesConfig {

    @Bean
    public UserRequestProcessor userRequestProcessor(@NonNull UserJpaRepositoryWrapper userJpaRepositoryWrapper,
                                                     @NonNull UserMembershipJpaRepositoryWrapper userMembershipJpaRepositoryWrapper,
                                                     @NonNull RoleJpaRepositoryWrapper roleJpaRepositoryWrapper){
        return new UserRequestProcessingService(
                userJpaRepositoryWrapper,
                userMembershipJpaRepositoryWrapper,
                roleJpaRepositoryWrapper
        );
    }

    @Bean
    public RoleRequestProcessor roleRequestProcessor(@NonNull RoleJpaRepositoryWrapper roleJpaRepositoryWrapper){
        return new RoleRequestProcessingService(roleJpaRepositoryWrapper);
    }
}
