package com.olson1998.authdata.domain.service.security;

import com.olson1998.authdata.domain.model.security.MappedTenantSecret;
import com.olson1998.authdata.domain.port.data.utils.ExtendedTrustedIssuer;
import com.olson1998.authdata.domain.port.security.stereotype.TenantSecret;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TenantSecretMapper {

    public Optional<TenantSecret> map(List<ExtendedTrustedIssuer> extendedTrustedIssuerList){
        if(extendedTrustedIssuerList == null || extendedTrustedIssuerList.isEmpty()){
            return Optional.empty();
        }else {
            return mapByAudience(extendedTrustedIssuerList);
        }
    }

    private Optional<TenantSecret> mapByAudience(List<ExtendedTrustedIssuer> extendedTrustedIssuerList){
        try{
            var tenantSecretBuilder = MappedTenantSecret.builder();
            var size = extendedTrustedIssuerList.size();
            var tid = extendedTrustedIssuerList.stream().map(ExtendedTrustedIssuer::getTenantId)
                    .findFirst()
                    .orElseThrow();
            tenantSecretBuilder.tenantId(tid);
            var alg = extendedTrustedIssuerList.stream().map(ExtendedTrustedIssuer::getAlgorithm)
                    .findFirst()
                    .orElseThrow();
            tenantSecretBuilder.algorithm(alg);
            var tmp = extendedTrustedIssuerList.stream().map(ExtendedTrustedIssuer::getTimestamp)
                    .findFirst()
                    .orElseThrow();
            tenantSecretBuilder.timestamp(tmp);
            var trustedIssuers = extendedTrustedIssuerList.stream().map(ExtendedTrustedIssuer::getName)
                    .toList()
                    .toArray(new String[size]);
            tenantSecretBuilder.trustedIssuers(trustedIssuers);
            return Optional.of(tenantSecretBuilder.build());
        }catch (NoSuchElementException e){
            return Optional.empty();
        }
    }

}
