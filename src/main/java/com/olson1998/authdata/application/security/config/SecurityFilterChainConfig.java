package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.application.security.filter.CheckpointAuthenticationFilter;
import com.olson1998.authdata.application.security.filter.JwtAuthenticationFilter;
import com.olson1998.authdata.application.security.handler.MicroserviceAuthenticationFailureHandler;
import com.olson1998.authdata.application.security.service.ApplicationAuthenticationManager;
import com.olson1998.authdata.domain.port.security.repository.TokenVerifier;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfig {

    private static final String[] CHECKPOINT_TOKEN_AUTH_PATHS = {
            "/authority/data/**",
            "/role/data/**",
            "/user/data/**",
            "/checkpoint/logs"
    };

    private static final String[] JWT_TOKEN_AUTH_PATHS = {
            "/checkpoint/create"
    };

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new MicroserviceAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationConverter authenticationConverter(){
        return new BasicAuthenticationConverter();
    }

    @Bean
    public SecurityFilterChain jwtSecurityFilterChain(@NonNull HttpSecurity security,
                                                      @NonNull TokenVerifier tokenVerifier,
                                                      @NonNull ApplicationAuthenticationManager authenticationManager,
                                                      @NonNull AuthenticationConverter authenticationConverter,
                                                      @NonNull AuthenticationFailureHandler authenticationFailureHandler) throws Exception {
        var jwtTokenFilter = jwtAuthenticationFilter(tokenVerifier, authenticationManager, authenticationConverter);
        jwtTokenFilter.setFailureHandler(authenticationFailureHandler);
        security.csrf().disable();
        security.authorizeHttpRequests(requestsStream -> requestsStream.requestMatchers(JWT_TOKEN_AUTH_PATHS).permitAll());
        security.securityMatcher(JWT_TOKEN_AUTH_PATHS).addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter.class);
        return security.build();
    }

    @Bean
    public SecurityFilterChain checkpointSecurityFilterChain(@NonNull HttpSecurity security,
                                                             @NonNull TokenVerifier tokenVerifier,
                                                             @NonNull ApplicationAuthenticationManager authenticationManager,
                                                             @NonNull AuthenticationConverter authenticationConverter,
                                                             @NonNull AuthenticationFailureHandler authenticationFailureHandler) throws Exception {
        var checkpointTokenFilter = checkpointAuthenticationFilter(
                tokenVerifier,
                authenticationManager,
                authenticationConverter
        );
        checkpointTokenFilter.setFailureHandler(authenticationFailureHandler);
        security.csrf().disable();
        security.authorizeHttpRequests(requestsStream -> requestsStream.requestMatchers(CHECKPOINT_TOKEN_AUTH_PATHS).permitAll());
        security.securityMatcher(CHECKPOINT_TOKEN_AUTH_PATHS).addFilterBefore(checkpointTokenFilter, BasicAuthenticationFilter.class);
        return security.build();
    }

    private CheckpointAuthenticationFilter checkpointAuthenticationFilter(@NonNull TokenVerifier tokenVerifier,
                                                                          @NonNull ApplicationAuthenticationManager authenticationManager,
                                                                          @NonNull AuthenticationConverter authenticationConverter){
        return new CheckpointAuthenticationFilter(
                tokenVerifier,
                authenticationManager,
                authenticationConverter
        );
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter(@NonNull TokenVerifier tokenVerifier,
                                                            @NonNull ApplicationAuthenticationManager authenticationManager,
                                                            @NonNull AuthenticationConverter authenticationConverter){
        return new JwtAuthenticationFilter(
                tokenVerifier,
                authenticationManager,
                authenticationConverter
        );
    }
}
