package com.olson1998.authdata.domain.port.data.stereotype;

import com.auth0.jwt.algorithms.Algorithm;

public interface TenantAlgorithm {

    String getTenantId();

    long getTimestamp();

    Algorithm toAlgorithm();

}
