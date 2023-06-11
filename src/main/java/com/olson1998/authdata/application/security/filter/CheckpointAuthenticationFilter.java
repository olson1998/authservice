package com.olson1998.authdata.application.security.filter;

import com.olson1998.authdata.domain.port.security.repository.TokenVerifier;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;

import java.io.IOException;
import java.util.Optional;

import static com.olson1998.authdata.application.security.filter.CommonResponseHeaders.CHECKPOINT_TOKEN_HEADER;

@Slf4j
public class CheckpointAuthenticationFilter extends AuthDataAuthenticationFilter {

    @Override
    protected void logContextBuild(RequestContext context) {
        log.trace("Building request context based on checkpoint: '{}' of tenant: '{}' of user: {}",
                context.getId(),
                context.getTenantId(),
                context.getUserId()
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = Optional.ofNullable(request.getHeader(CHECKPOINT_TOKEN_HEADER))
                .orElseThrow();
        var context = tokenVerifier.verifyCheckpointToken(token);
        commonFilterChain(context, request, response, filterChain);
    }

    public CheckpointAuthenticationFilter(TokenVerifier tokenVerifier, AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        super(tokenVerifier, authenticationManager, authenticationConverter);
    }
}
