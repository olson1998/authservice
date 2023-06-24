package com.olson1998.authdata.application.security.filter;

import com.olson1998.authdata.domain.port.security.repository.JwtAuthenticationConverter;
import com.olson1998.authdata.domain.port.security.repository.JwtAuthenticationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Slf4j
public class JwtAuthenticationFilter extends AuthenticationFilter {

    public JwtAuthenticationFilter(JwtAuthenticationManager jwtAuthenticationManager, JwtAuthenticationConverter jwtAuthenticationConverter) {
        super(jwtAuthenticationManager, jwtAuthenticationConverter);
    }

}
