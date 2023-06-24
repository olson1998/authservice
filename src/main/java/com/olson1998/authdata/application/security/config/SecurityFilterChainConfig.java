package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.application.security.filter.CheckpointAuthenticationFilter;
import com.olson1998.authdata.application.security.filter.CheckpointRequestFilter;
import com.olson1998.authdata.application.security.filter.JwtAuthenticationFilter;
import com.olson1998.authdata.domain.port.security.repository.*;
import com.olson1998.authdata.domain.service.security.CheckpointAuthenticationService;
import com.olson1998.authdata.domain.service.security.CheckpointRequestManagingService;
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
    public AuthDataAuthenticationSuccessHandler authDataAuthenticationSuccessHandler(@NonNull RequestContextFactory requestContextFactory){
        return new SuccessfulAuthenticationService(requestContextFactory);
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

    @Bean
    public SecurityFilterChain checkpointSecurityFilterChain(@NonNull HttpSecurity security,
                                                             @NonNull CheckpointVerifier checkpointVerifier,
                                                             @NonNull CheckpointAuthenticationConverter checkpointAuthenticationConverter,
                                                             @NonNull CheckpointAuthenticationFailureHandler checkpointAuthenticationFailureHandler,
                                                             @NonNull AuthDataAuthenticationSuccessHandler authDataAuthenticationSuccessHandler) throws Exception {
        var checkpointTokenFilter = checkpointAuthenticationFilter(
                new CheckpointAuthenticationService(checkpointVerifier),
                checkpointAuthenticationConverter
        );
        setHandlers(checkpointTokenFilter, authDataAuthenticationSuccessHandler, checkpointAuthenticationFailureHandler);
        security.csrf().disable();
        security.authorizeHttpRequests(requestsStream -> requestsStream.requestMatchers(CHECKPOINT_TOKEN_AUTH_PATHS).permitAll());
        security.securityMatcher(CHECKPOINT_TOKEN_AUTH_PATHS).addFilterBefore(checkpointTokenFilter, BasicAuthenticationFilter.class);
        return security.build();
    }

    @Bean
    public SecurityFilterChain checkpointRequestFilterChain(@NonNull HttpSecurity security,
                                                            @NonNull CheckpointVerifier checkpointVerifier,
                                                            @NonNull CheckpointAuthenticationConverter checkpointAuthenticationConverter,
                                                            @NonNull CheckpointAuthenticationFailureHandler checkpointAuthenticationFailureHandler,
                                                            @NonNull AuthDataAuthenticationSuccessHandler authDataAuthenticationSuccessHandler) throws Exception {
        var checkpointTokenFilter = checkpointRequestFilter(
                new CheckpointRequestManagingService(checkpointVerifier),
                checkpointAuthenticationConverter
        );
        setHandlers(checkpointTokenFilter, authDataAuthenticationSuccessHandler, checkpointAuthenticationFailureHandler);
        security.csrf().disable();
        security.authorizeHttpRequests(requestsStream -> requestsStream.requestMatchers(CHECKPOINT_REQUEST_PATHS).permitAll());
        security.securityMatcher(CHECKPOINT_REQUEST_PATHS).addFilterBefore(checkpointTokenFilter, BasicAuthenticationFilter.class);
        return security.build();
    }

    private CheckpointAuthenticationFilter checkpointAuthenticationFilter(CheckpointAuthenticationManager checkpointAuthenticationManager,
                                                                          CheckpointAuthenticationConverter checkpointAuthenticationConverter){
        return new CheckpointAuthenticationFilter(
                checkpointAuthenticationManager,
                checkpointAuthenticationConverter
        );
    }

    private CheckpointRequestFilter checkpointRequestFilter(CheckpointRequestManager checkpointRequestManager,
                                                            CheckpointAuthenticationConverter checkpointAuthenticationConverter){
        return new CheckpointRequestFilter(
                checkpointRequestManager,
                checkpointAuthenticationConverter
        );
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
