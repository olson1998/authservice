package com.olson1998.authdata.application.datasource.entity.global.values;

import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.application.datasource.entity.global.TenantSecretData;
import com.olson1998.authdata.application.datasource.entity.global.TrustedIssuerData;
import com.olson1998.authdata.domain.port.data.utils.ExtendedTrustedIssuer;
import com.olson1998.authdata.domain.port.data.utils.PasswordEncryptionType;
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
    public PasswordEncryptionType getUserPasswordEncryptionType() {
        return tenantSecretData.getPasswordEncryptionType();
    }

    @Override
    public String getName() {
        return trustedIssuerData.getName();
    }
}
