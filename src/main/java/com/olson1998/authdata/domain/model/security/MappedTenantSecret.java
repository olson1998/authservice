package com.olson1998.authdata.domain.model.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.domain.port.data.utils.PasswordEncryptionType;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;

@Getter
@Builder
public class MappedTenantSecret implements TenantSecret {

    private String tenantId;

    private long timestamp;

    private PasswordEncryptionType passwordEncryptionType;

    private Algorithm algorithm;

    private String[] trustedIssuers;

    @Override
    public PasswordEncryptionType getPasswordEncryptionType() {
        return passwordEncryptionType;
    }

    @Override
    public Algorithm toAlgorithm() {
        return algorithm;
    }

    @Override
    public String getUsername() {
        return tenantId;
    }

    @Override
    public String getPassword() {
        return algorithm.toString();
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Duration getUserExpDuration() {
        return null;
    }

    @Override
    public Duration getPasswordExpDuration() {
        return null;
    }

    @Override
    public boolean isUserPasswordExpiring() {
        return false;
    }

    @Override
    public boolean isUserExpiring() {
        return false;
    }

}
