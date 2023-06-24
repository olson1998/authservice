package com.olson1998.authdata.domain.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.olson1998.authdata.domain.model.exception.security.JwtVerificationFailed;
import com.olson1998.authdata.domain.model.exception.security.TenantSecretNotFound;
import com.olson1998.authdata.domain.model.security.CheckpointAuthentication;
import com.olson1998.authdata.domain.port.security.repository.JwtAuthenticationManager;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.stereotype.ServiceInstanceSign;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.NoSuchElementException;

import static com.olson1998.authdata.domain.service.security.RequestContextFabricationService.TENANT_ID;

@RequiredArgsConstructor
public class JwtAuthenticationService implements JwtAuthenticationManager {

    private static final String CANNOT_CAST_AUTHENTICATION =
            "cannot cast principal to " + DecodedJWT.class.getName();

    private final TenantSecretProvider tenantSecretProvider;

    private final ServiceInstanceSign serviceInstanceSign;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            var decodedJwt = (DecodedJWT) authentication.getPrincipal();
            var tid = decodedJwt.getClaim(TENANT_ID).asString();
            var tenantSecret = tenantSecretProvider.getTenantSecret(tid)
                    .orElseThrow();
            var jwtVerifier = buildVerifier(tenantSecret);
            jwtVerifier.verify(decodedJwt);
            authentication.setAuthenticated(true);
            return authentication;
        }catch (ClassCastException e){
            throw new InternalAuthenticationServiceException(
                    CANNOT_CAST_AUTHENTICATION,
                    e
            );
        }catch (NoSuchElementException e){
            throw new TenantSecretNotFound(e);
        }catch (JWTVerificationException e){
            throw new JwtVerificationFailed(e);
        }
    }

    private JWTVerifier buildVerifier(TenantSecret tenantSecret){
        var alg = tenantSecret.toAlgorithm();
        var tid = tenantSecret.getTenantId();
        var trustedIssuers = tenantSecret.getTrustedIssuers();
        return JWT.require(alg)
                .withAudience(serviceInstanceSign.getValue())
                .withClaim(TENANT_ID, tid)
                .withIssuer(trustedIssuers)
                .build();
    }
}
