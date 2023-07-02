package com.olson1998.authdata.application.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.InetAddress;

import static com.olson1998.authdata.domain.model.GlobalTestDataSet.TEST_TENANT_ID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TenantSecretTestDataSet {

    public static final String TEST_TENANT_SECRET_VAL = "abc";

    public static final long TEST_TIMESTAMP = System.currentTimeMillis();

    public static final Algorithm TEST_ALGORITHM = Algorithm.HMAC256(TEST_TENANT_SECRET_VAL);

    private static final String TEST_THIS_SERVICE_SIGN = new StringBuilder(InetAddress.getLoopbackAddress().toString())
            .append(':')
            .append(8080)
            .toString();

    public static final String[] TEST_TRUSTED_ISS = {
            TEST_THIS_SERVICE_SIGN
    };

    public static TenantSecret TEST_TENANT_SECRET = MappedTenantSecret.builder()
            .tenantId(TEST_TENANT_ID)
            .trustedIssuers(TEST_TRUSTED_ISS)
            .timestamp(TEST_TIMESTAMP)
            .algorithm(TEST_ALGORITHM)
            .build();
}
