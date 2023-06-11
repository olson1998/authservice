package com.olson1998.authdata.application.datasource.entity.utils;

import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.application.datasource.entity.TenantSecretData;
import com.olson1998.authdata.application.datasource.entity.TrustedIssuerData;
import com.olson1998.authdata.domain.port.data.utils.ExtendedTrustedIssuer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExtendedTrustedIssuerData implements ExtendedTrustedIssuer {

    private final TenantSecretData tenantSecretData;

    private final TrustedIssuerData trustedIssuerData;

    @Override
    public long getTimestamp() {
        return tenantSecretData.getTimestamp();
    }

    @Override
    public String getTenantId() {
        return tenantSecretData.getTenantId();
    }

    @Override
    public Algorithm getAlgorithm() {
        return tenantSecretData.toAlgorithm();
    }

    @Override
    public String getName() {
        return trustedIssuerData.getName();
    }
}
