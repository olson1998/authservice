package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.domain.port.security.repository.JwtAuthenticationConverter;
import com.olson1998.authdata.domain.port.security.repository.JwtAuthenticationFailureHandler;
import com.olson1998.authdata.domain.service.security.JwtAuthenticationConvertingService;
import com.olson1998.authdata.domain.service.security.JwtFailureHandlingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtAuthConfig {

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        return new JwtAuthenticationConvertingService();
    }

    @Bean
    public JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler(){
        return new JwtFailureHandlingService();
    }
}
