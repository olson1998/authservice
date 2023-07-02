package com.olson1998.authdata.application.requesting.config;

import com.olson1998.authdata.application.datasource.repository.tenant.wrapper.*;
import com.olson1998.authdata.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.repository.UserPasswordEnigma;
import com.olson1998.authdata.domain.service.processing.request.AuthorityRequestProcessingService;
import com.olson1998.authdata.domain.service.processing.request.RoleRequestProcessingService;
import com.olson1998.authdata.domain.service.processing.request.UserRequestProcessingService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntitiesRequestProcessorsConfig {

    @Bean
    public AuthorityRequestProcessor authorityRequestProcessor(@NonNull AuthorityJpaRepositoryWrapper authorityJpaRepositoryWrapper,
                                                               @NonNull RoleJpaRepositoryWrapper roleJpaRepositoryWrapper,
                                                               @NonNull RoleBindingJpaRepositoryWrapper roleBindingJpaRepositoryWrapper){
        return new AuthorityRequestProcessingService(
                authorityJpaRepositoryWrapper,
                roleBindingJpaRepositoryWrapper,
                roleJpaRepositoryWrapper
        );
    }

    @Bean
    public RoleRequestProcessor roleRequestProcessor(@NonNull AuthorityRequestProcessor authorityRequestProcessor,
                                                     @NonNull RoleJpaRepositoryWrapper roleJpaRepositoryWrapper,
                                                     @NonNull RoleBindingJpaRepositoryWrapper roleBindingJpaRepositoryWrapper){
        return new RoleRequestProcessingService(
                authorityRequestProcessor,
                roleJpaRepositoryWrapper,
                roleBindingJpaRepositoryWrapper
        );
    }

    @Bean
    public UserRequestProcessor userRequestProcessor(@NonNull TenantSecretProvider tenantSecretProvider,
                                                     @NonNull UserPasswordEnigma userPasswordEnigma,
                                                     @NonNull RoleRequestProcessor roleRequestProcessor,
                                                     @NonNull UserJpaRepositoryWrapper userJpaRepositoryWrapper,
                                                     @NonNull UserPasswordJpaRepositoryWrapper userPasswordJpaRepositoryWrapper,
                                                     @NonNull UserMembershipJpaRepositoryWrapper userMembershipJpaRepositoryWrapper){
        return new UserRequestProcessingService(
                tenantSecretProvider,
                userPasswordEnigma,
                roleRequestProcessor,
                userJpaRepositoryWrapper,
                userPasswordJpaRepositoryWrapper,
                userMembershipJpaRepositoryWrapper
        );
    }
}
