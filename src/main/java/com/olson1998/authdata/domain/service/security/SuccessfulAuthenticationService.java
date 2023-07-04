package com.olson1998.authdata.domain.service.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.olson1998.authdata.domain.model.security.JwtAuthentication;
import com.olson1998.authdata.domain.port.processing.request.repository.RequestContextHolder;
import com.olson1998.authdata.domain.port.security.repository.AuthDataAuthenticationSuccessHandler;
import com.olson1998.authdata.domain.port.security.repository.RequestContextFactory;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class SuccessfulAuthenticationService implements AuthDataAuthenticationSuccessHandler {

    private static final String REQUEST_CONTEXT_ID = "X-Request-Id";

    private static final String REQUEST_AUTH_TYPE = "X-Request-Auth-Type";

    private static final String REQUEST_AUTH_ID = "X-Request-Auth-Id";

    private static final String REQUEST_CONTEXT_TENANT = "X-Request-Tenant";

    private static final String REQUEST_CONTEXT_USER = "X-Request-User";

    private final RequestContextHolder requestContextHolder;

    private final RequestContextFactory requestContextFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        RequestContext context = null;
        String authId = null;
        log.trace("Building context on Thread: {}", Thread.currentThread().getId());
        if (authentication instanceof JwtAuthentication jwtAuthentication) {
            var decodedJwt = (DecodedJWT) jwtAuthentication.getPrincipal();
            authId = decodedJwt.getId();
            log.debug("Attempt building request context base on json web token: '{}'",decodedJwt.getId());
            context = requestContextFactory.fabricate(decodedJwt);
        }
        if(context != null){
            response.addHeader(REQUEST_CONTEXT_ID, context.getId().toString());
            response.addHeader(REQUEST_AUTH_TYPE, authentication.getClass().getName());
            response.addHeader(REQUEST_AUTH_ID, authId);
            response.addHeader(REQUEST_CONTEXT_TENANT, context.getTenantId());
            response.addHeader(REQUEST_CONTEXT_USER, String.valueOf(context.getUserId()));
            requestContextHolder.setCurrentContext(context);
        }
    }
}
