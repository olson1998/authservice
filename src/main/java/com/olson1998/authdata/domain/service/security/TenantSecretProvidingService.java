package com.olson1998.authdata.domain.service.security;

import com.olson1998.authdata.domain.port.caching.repository.impl.TenantSecretCacheRepository;
import com.olson1998.authdata.domain.port.data.repository.TenantSecretDataSourceRepository;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TenantSecretProvidingService implements TenantSecretProvider {

    private final TenantSecretCacheRepository tenantSecretCacheRepository;

    private final TenantSecretDataSourceRepository tenantSecretDataSourceRepository;

    private final TenantSecretMapper tenantSecretMapper = new TenantSecretMapper();

    @Override
    public Optional<TenantSecret> getTenantSecret(String tid) {
        var optionalTenantSec = tenantSecretCacheRepository.getValue(tid);
        if(optionalTenantSec.isPresent()){
            var tmp = optionalTenantSec.get().getTimestamp();
            if(tenantSecretDataSourceRepository.isMostRecentTimestamp(tid, tmp)){
                return optionalTenantSec;
            }else {
                optionalTenantSec = obtainFromDataSource(tid);
                if(optionalTenantSec.isPresent()){
                    tenantSecretCacheRepository.cacheValue(tid, optionalTenantSec.get());
                    return optionalTenantSec;
                }else {
                    throw new UnknownError("cached tenant not present in data source");
                }
            }
        }else {
            optionalTenantSec = obtainFromDataSource(tid);
            if(optionalTenantSec.isPresent()){
                tenantSecretCacheRepository.cacheValue(tid, optionalTenantSec.get());
                return optionalTenantSec;
            }else {
                throw new UnknownError("cached tenant not present in data source");
            }
        }
    }

    private Optional<TenantSecret> obtainFromDataSource(String tid){
        var tenantAudienceList = tenantSecretDataSourceRepository.getTenantAudience(tid);
        return tenantSecretMapper.map(tenantAudienceList);
    }

}
