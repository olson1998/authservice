package com.olson1998.authdata.domain.service.security;

import com.olson1998.authdata.domain.port.security.repository.CheckpointAuthenticationManager;
import com.olson1998.authdata.domain.port.security.repository.CheckpointVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class CheckpointAuthenticationService implements CheckpointAuthenticationManager {

    private final CheckpointVerifier checkpointVerifier;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return checkpointVerifier.attemptAuthentication(authentication, true);
    }

}