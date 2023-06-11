package com.olson1998.authdata.application.security.filter;

import com.olson1998.authdata.application.requesting.AdapterRequestContextHolder;
import com.olson1998.authdata.domain.port.security.repository.TokenVerifier;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import java.io.IOException;

import static com.olson1998.authdata.application.security.filter.CommonResponseHeaders.*;

public abstract class AuthDataAuthenticationFilter extends AuthenticationFilter {

    protected final TokenVerifier tokenVerifier;

    protected abstract void logContextBuild(RequestContext context);

    protected abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException;

    protected void commonFilterChain(RequestContext context, HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        AdapterRequestContextHolder.setLocalThreadRequestContext(context);
        logContextBuild(context);
        response.addHeader(REQUEST_CONTEXT_ID, context.getId().toString());
        response.addHeader(REQUEST_CONTEXT_TENANT, context.getTenantId());
        response.addHeader(REQUEST_CONTEXT_USER, String.valueOf(context.getUserId()));
        request.authenticate(response);
        filterChain.doFilter(request, response);
    }

    public AuthDataAuthenticationFilter(TokenVerifier tokenVerifier,
                                        AuthenticationManager authenticationManager,
                                        AuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);
        this.tokenVerifier = tokenVerifier;
    }

}
