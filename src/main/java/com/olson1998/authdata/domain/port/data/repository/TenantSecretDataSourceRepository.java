package com.olson1998.authdata.domain.port.data.repository;

import com.olson1998.authdata.domain.port.data.stereotype.TenantAlgorithm;
import com.olson1998.authdata.domain.port.data.utils.ExtendedTrustedIssuer;

import java.util.List;
import java.util.Optional;

public interface TenantSecretDataSourceRepository {

    boolean isMostRecentTimestamp(String tid, long timestamp);

    Optional<TenantAlgorithm> getTenantAlgorithm(String tid);

    List<ExtendedTrustedIssuer> getTenantAudience(String tid);
}
