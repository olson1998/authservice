package com.olson1998.authdata.domain.service.security;

import com.auth0.jwt.JWT;
import com.olson1998.authdata.domain.model.security.JwtAuthentication;
import com.olson1998.authdata.domain.port.security.repository.JwtAuthenticationConverter;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class JwtAuthenticationConvertingService implements JwtAuthenticationConverter {

    private static final String BEARER = "Bearer ";

    @Override
    public Authentication convert(HttpServletRequest request) {
        var token = StringUtils.substringAfter(request.getHeader(AUTHORIZATION), BEARER);
        var decodedJwt = JWT.decode(token);
        return new JwtAuthentication(decodedJwt);
    }
}
