package com.olson1998.authdata.application.security.filter;

import com.olson1998.authdata.application.requesting.AdapterRequestContextHolder;
import com.olson1998.authdata.domain.port.security.repository.TokenVerifier;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class JwtAuthenticationFilter extends AuthDataAuthenticationFilter {

    private static final String BEARER = "Bearer ";

    @Override
    protected void logContextBuild(RequestContext context) {
        log.trace("Building request context of jwt: '{}' of tenant: '{}' of user: {}",
                context.getId(),
                context.getTenantId(),
                context.getUserId()
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = StringUtils.substringAfter(request.getHeader(AUTHORIZATION), BEARER);
        var context = tokenVerifier.verifyJwt(token);
        commonFilterChain(context, request, response, filterChain);
    }

    public JwtAuthenticationFilter(TokenVerifier tokenVerifier, AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        super(tokenVerifier, authenticationManager, authenticationConverter);
    }

}
