package com.olson1998.authdata.application.datasource.repository.global.spring;

import com.olson1998.authdata.application.datasource.entity.global.TenantSecretData;
import com.olson1998.authdata.application.datasource.entity.global.values.ExtendedTrustedIssuerData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantSecretJpaRepository extends JpaRepository<TenantSecretData, String> {

    @Query("SELECT CASE WHEN COUNT(sd.tenantId) >0 THEN true ELSE false END FROM TenantSecretData sd WHERE sd.tenantId=:tid")
    boolean isTenantExisting(String tid);

    @Query("SELECT CASE WHEN sd.timestamp=:timestamp THEN true ELSE false END FROM TenantSecretData sd WHERE sd.tenantId=:tid")
    boolean isMostRecentTimestamp(String tid, long timestamp);

    @Query("SELECT sd FROM TenantSecretData sd WHERE sd.tenantId=:tid")
    Optional<TenantSecretData> selectTenantSecretByTid(String tid);

    @Query("SELECT new com.olson1998.authdata.application.datasource.entity.global.values.ExtendedTrustedIssuerData" +
            "(sd, ti) " +
            "FROM TenantSecretData sd " +
            "LEFT OUTER JOIN TrustedIssuerData ti ON sd.tenantId=ti.trustedIssuerId.tenantId " +
            "WHERE sd.tenantId=:tid")
    List<ExtendedTrustedIssuerData> selectTrustedIssuersByTid(String tid);
}
