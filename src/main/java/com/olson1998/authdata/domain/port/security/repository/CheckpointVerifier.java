package com.olson1998.authdata.domain.port.security.repository;

import org.springframework.security.core.Authentication;

public interface CheckpointVerifier {

    Authentication attemptAuthentication(Authentication authentication, boolean verifyToken);
}
