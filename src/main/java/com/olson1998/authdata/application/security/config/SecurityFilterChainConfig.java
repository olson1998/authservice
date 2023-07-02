package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.application.requesting.AdapterRequestContextHolder;
import com.olson1998.authdata.application.security.filter.JwtAuthenticationFilter;
import com.olson1998.authdata.domain.port.security.repository.*;
import com.olson1998.authdata.domain.service.security.JwtAuthenticationService;
import com.olson1998.authdata.domain.service.security.SuccessfulAuthenticationService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private static final String[] CHECKPOINT_TOKEN_AUTH_PATHS = {
            "/authority/data/**",
            "/role/data/**",
            "/user/data/**",
            "/auth/data/**"
    };

    private static final String[] CHECKPOINT_REQUEST_PATHS = {
            "/checkpoint/logs"
    };

    private static final String[] JWT_TOKEN_AUTH_PATHS = {
            "/checkpoint/create"
    };

    @Bean
    public AuthDataAuthenticationSuccessHandler authDataAuthenticationSuccessHandler(@NonNull AdapterRequestContextHolder adapterRequestContextHolder,
                                                                                     @NonNull RequestContextFactory requestContextFactory){
        return new SuccessfulAuthenticationService(adapterRequestContextHolder, requestContextFactory);
    }

    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(@NonNull HttpSecurity security,
                                                      @NonNull TenantSecretProvider tenantSecretProvider,
                                                      @NonNull LocalServiceInstanceSign localServiceInstanceSign,
                                                      @NonNull JwtAuthenticationConverter jwtAuthenticationConverter,
                                                      @NonNull JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler,
                                                      @NonNull AuthDataAuthenticationSuccessHandler authDataAuthenticationSuccessHandler) throws Exception {
        var jwtTokenFilter = jwtAuthenticationFilter(
                new JwtAuthenticationService(tenantSecretProvider, localServiceInstanceSign),
                jwtAuthenticationConverter
        );
        setHandlers(jwtTokenFilter, authDataAuthenticationSuccessHandler, jwtAuthenticationFailureHandler);
        security.csrf().disable();
        security.authorizeHttpRequests(requestsStream -> requestsStream.requestMatchers(JWT_TOKEN_AUTH_PATHS).permitAll());
        security.securityMatcher(JWT_TOKEN_AUTH_PATHS).addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class);
        return security.build();
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter(@NonNull JwtAuthenticationManager jwtAuthenticationManager,
                                                            @NonNull JwtAuthenticationConverter jwtAuthenticationConverter){
        return new JwtAuthenticationFilter(
                jwtAuthenticationManager,
                jwtAuthenticationConverter
        );
    }

    private void setHandlers(AuthenticationFilter authenticationFilter,
                             AuthenticationSuccessHandler authenticationSuccessHandler,
                             AuthenticationFailureHandler authenticationFailureHandler){
        authenticationFilter.setFailureHandler(authenticationFailureHandler);
        authenticationFilter.setSuccessHandler(authenticationSuccessHandler);
    }
}
