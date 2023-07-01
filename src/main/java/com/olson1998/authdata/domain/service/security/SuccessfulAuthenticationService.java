package com.olson1998.authdata.domain.service.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.olson1998.authdata.domain.model.security.CheckpointAuthentication;
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

@Slf4j
@RequiredArgsConstructor
public class SuccessfulAuthenticationService implements AuthDataAuthenticationSuccessHandler {

    private static final String REQUEST_CONTEXT_ID = "X-Request-id";

    private static final String REQUEST_CONTEXT_TENANT = "X-Request-tenant";

    private static final String REQUEST_CONTEXT_USER = "X-Request-user";

    private final RequestContextHolder requestContextHolder;

    private final RequestContextFactory requestContextFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        RequestContext context = null;
        if(authentication instanceof CheckpointAuthentication checkpointAuthentication){
            log.debug("Attempt building request context base on checkpoint: '{}'", checkpointAuthentication.getCheckpoint().getId());
            context = requestContextFactory.fabricate(
                    checkpointAuthentication.getxCheckpointToken(),
                    checkpointAuthentication.getCheckpointTimestamp(),
                    checkpointAuthentication.getCheckpoint()
            );
        } else if (authentication instanceof JwtAuthentication jwtAuthentication) {
            var decodedJwt = (DecodedJWT) jwtAuthentication.getPrincipal();
            log.debug("Attempt building request context base on json web token: '{}'",decodedJwt.getId());
            context = requestContextFactory.fabricate(decodedJwt);
        }
        if(context != null){
            response.addHeader(REQUEST_CONTEXT_ID, context.getId().toString());
            response.addHeader(REQUEST_CONTEXT_TENANT, context.getTenantId());
            response.addHeader(REQUEST_CONTEXT_USER, String.valueOf(context.getUserId()));
            requestContextHolder.setCurrentContext(context);
        }
    }
}
