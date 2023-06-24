package com.olson1998.authdata.domain.model.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.domain.port.data.utils.SecretAlgorithm;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MappedTenantSecret implements TenantSecret {

    private String tenantId;

    private long timestamp;

    private Algorithm algorithm;

    private String[] trustedIssuers;

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
    public SecretAlgorithm getSecretDigestAlgorithm() {
        return null;
    }
}
