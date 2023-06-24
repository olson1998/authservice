package com.olson1998.authdata.domain.service.security;

import com.olson1998.authdata.domain.port.security.exception.JwtAuthenticationException;
import com.olson1998.authdata.domain.port.security.repository.JwtAuthenticationFailureHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public class JwtFailureHandlingService implements JwtAuthenticationFailureHandler {

    private static final String JWT_TOKEN_AUTH_FAIL_REASON = "X-Jwt-auth-fail";

    private static final String JWT_TOKEN_AUTH_FAIL_REASON_FORMAT = "{\"reason\":\"%s\"}";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(401);
        response.setHeader(JWT_TOKEN_AUTH_FAIL_REASON, exception.getClass().getName());
        if(exception instanceof JwtAuthenticationException jwtAuthenticationException){
            var body = String.format(JWT_TOKEN_AUTH_FAIL_REASON_FORMAT, jwtAuthenticationException.getCause().getMessage());
            response.setContentType(APPLICATION_JSON.toString());
            response.getWriter().write(body);
        }
    }
}
