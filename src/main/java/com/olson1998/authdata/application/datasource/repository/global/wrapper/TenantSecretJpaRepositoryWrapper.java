package com.olson1998.authdata.application.datasource.repository.global.wrapper;

import com.olson1998.authdata.application.datasource.repository.global.spring.TenantSecretJpaRepository;
import com.olson1998.authdata.domain.port.data.repository.TenantSecretDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.TenantAlgorithm;
import com.olson1998.authdata.domain.port.data.utils.ExtendedTrustedIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantSecretJpaRepositoryWrapper implements TenantSecretDataSourceRepository {

    private final TenantSecretJpaRepository tenantSecretJpaRepository;

    @Override
    public boolean isMostRecentTimestamp(String tid, long timestamp) {
        return tenantSecretJpaRepository.isMostRecentTimestamp(tid, timestamp);
    }

    @Override
    public Optional<TenantAlgorithm> getTenantAlgorithm(String tid) {
        return tenantSecretJpaRepository.selectTenantSecretByTid(tid)
                .map(TenantAlgorithm.class::cast);
    }

    @Override
    public List<ExtendedTrustedIssuer> getTenantAudience(String tid) {
        return tenantSecretJpaRepository.selectTrustedIssuersByTid(tid).stream()
                .map(ExtendedTrustedIssuer.class::cast)
                .toList();
    }
}
