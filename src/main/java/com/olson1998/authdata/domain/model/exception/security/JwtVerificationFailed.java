package com.olson1998.authdata.domain.model.exception.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.olson1998.authdata.domain.port.security.exception.JwtAuthenticationException;

public class JwtVerificationFailed extends JwtAuthenticationException {

    private static final String JWT_VERIFICATION_FAILED = "jwt verification failed";

    public JwtVerificationFailed(JWTVerificationException cause) {
        super(JWT_VERIFICATION_FAILED, cause);
    }
}
