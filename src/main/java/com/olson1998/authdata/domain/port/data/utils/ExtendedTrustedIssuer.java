package com.olson1998.authdata.domain.port.data.utils;

import com.auth0.jwt.algorithms.Algorithm;
import com.olson1998.authdata.domain.port.data.stereotype.TenantTrustedIssuer;

public interface ExtendedTrustedIssuer extends TenantTrustedIssuer {

    long getTimestamp();

    String getTenantId();

    Algorithm getAlgorithm();
}
