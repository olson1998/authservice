package com.olson1998.authdata.application.datasource.repository.global.spring;

import com.olson1998.authdata.application.datasource.entity.global.TenantDataSourceData;
import com.olson1998.authdata.application.datasource.entity.global.TenantSecretData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantDataSourceJpaRepository extends JpaRepository<TenantDataSourceData, Long> {

    @Query("SELECT ds FROM TrustedIssuerData ds WHERE ds.tenantId=:tid")
    Optional<TenantSecretData> selectTenantDataSourceByTenantId(String tid);
}
