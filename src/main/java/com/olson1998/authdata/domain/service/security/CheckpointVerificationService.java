package com.olson1998.authdata.domain.service.security;

import com.olson1998.authdata.domain.model.exception.security.TenantSecretNotFound;
import com.olson1998.authdata.domain.model.security.CheckpointAuthentication;
import com.olson1998.authdata.domain.port.security.repository.CheckpointVerifier;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
public class CheckpointVerificationService implements CheckpointVerifier {

    private final TenantSecretProvider tenantSecretProvider;

    private static final String CANNOT_CAST_AUTHENTICATION =
            "cannot cast authentication to " + CheckpointAuthentication.class.getName();

    @Override
    public Authentication attemptAuthentication(Authentication authentication, boolean verifyToken){
        try{
            var checkpointAuthentication = (CheckpointAuthentication) authentication;
            if(checkpointAuthentication.isError()){
                throw checkpointAuthentication.getCheckpointVerificationException();
            }else {
                var checkpoint = checkpointAuthentication.getCheckpoint();
                log.debug("Attempting authentication of checkpoint: '{}'", checkpoint.getId());
                var token = checkpointAuthentication.getxCheckpointToken();
                var tid = checkpoint.getTenantId();
                var tenantSecret = getTenantSecret(tid);
                if(verifyToken){
                    var authenticated = checkpoint.verifyCheckpointToken(token, tenantSecret.toAlgorithm().toString());
                    authentication.setAuthenticated(authenticated);
                }else {
                    authentication.setAuthenticated(true);
                }
                return checkpointAuthentication;
            }
        }catch (ClassCastException e){
            throw new InternalAuthenticationServiceException(CANNOT_CAST_AUTHENTICATION, e);
        }
    }

    private TenantSecret getTenantSecret(String tid){
        try{
            return tenantSecretProvider.getTenantSecret(tid)
                    .orElseThrow();
        }catch (NoSuchElementException e){
            throw new TenantSecretNotFound(e);
        }
    }
}
