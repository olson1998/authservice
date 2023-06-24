package com.olson1998.authdata.domain.model.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class JwtAuthentication implements Authentication {

    private boolean authenticated = false;

    private final DecodedJWT decodedJWT;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public Object getCredentials() {
        return decodedJWT;
    }

    @Override
    public Object getDetails() {
        return decodedJWT;
    }

    @Override
    public Object getPrincipal() {
        return decodedJWT;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return JwtAuthentication.class.getName();
    }
}
