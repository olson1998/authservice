package com.olson1998.authdata.application.developer.utils;

import com.olson1998.authdata.application.datasource.LocalThreadTenantDataSource;
import com.olson1998.authdata.application.datasource.entity.global.TenantDataSourceData;
import com.olson1998.authdata.application.datasource.entity.global.TenantDataSourceUserData;
import com.olson1998.authdata.application.datasource.entity.global.TenantSecretData;
import com.olson1998.authdata.application.datasource.entity.global.TrustedIssuerData;
import com.olson1998.authdata.application.datasource.entity.global.values.UserPasswordEncryption;
import com.olson1998.authdata.application.datasource.repository.global.spring.TenantDataSourceJpaRepository;
import com.olson1998.authdata.application.datasource.repository.global.spring.TenantDataSourceUserJpaRepository;
import com.olson1998.authdata.application.datasource.repository.global.spring.TenantSecretJpaRepository;
import com.olson1998.authdata.application.datasource.repository.global.spring.TrustedIssuerDataJpaRepository;
import com.olson1998.authdata.domain.port.processing.datasource.TenantSqlDataSourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.olson1998.authdata.application.datasource.entity.global.values.SqlDataSource.MARIA_DB;
import static com.olson1998.authdata.application.datasource.entity.global.values.JwtAlgorithm.HMAC256;
import static com.olson1998.authdata.application.datasource.entity.global.values.UserPasswordEncryption.ARGON2;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Slf4j
@Profile("developer")

@Component
@ConditionalOnProperty(value = "com.olson1998.authdata.application.developer.sample-data-inject", havingValue = "true")
public class SampleDataInject {

    private static final String DEV_TID = "TENANT_TEST";

    private static final TenantDataSourceData DEV_DB = TenantDataSourceData.builder()
            .tid(DEV_TID)
            .sqlDataSource(MARIA_DB)
            .host("localhost")
            .port(3306)
            .database("TENANT_TEST_AUTHDATA")
            .build();

    private final JwtTokenFactory jwtTokenFactory;

    private final TenantSecretJpaRepository tenantSecretJpaRepository;

    private final TrustedIssuerDataJpaRepository trustedIssuerDataJpaRepository;

    private final TenantDataSourceJpaRepository tenantDataSourceJpaRepository;

    private final TenantDataSourceUserJpaRepository tenantDataSourceUserJpaRepository;

    public SampleDataInject(JwtTokenFactory jwtTokenFactory,
                            TenantSecretJpaRepository tenantSecretJpaRepository,
                            TrustedIssuerDataJpaRepository trustedIssuerDataJpaRepository,
                            TenantDataSourceJpaRepository tenantDataSourceJpaRepository,
                            TenantDataSourceUserJpaRepository tenantDataSourceUserJpaRepository) {
        this.jwtTokenFactory = jwtTokenFactory;
        this.tenantSecretJpaRepository = tenantSecretJpaRepository;
        this.trustedIssuerDataJpaRepository = trustedIssuerDataJpaRepository;
        this.tenantDataSourceJpaRepository = tenantDataSourceJpaRepository;
        this.tenantDataSourceUserJpaRepository = tenantDataSourceUserJpaRepository;
    }

    @Modifying
    @Transactional(transactionManager = "globalDatasourceTransactionManager")
    public void injectTestTenant(){
        if(!tenantSecretJpaRepository.isTenantExisting(DEV_TID)){
            log.info("Injecting developer's test data set");
            tenantSecretJpaRepository.save(new TenantSecretData(
                    DEV_TID,
                    System.currentTimeMillis(),
                    randomAlphanumeric(10),
                    ARGON2,
                    HMAC256)
            );
            trustedIssuerDataJpaRepository.save(new TrustedIssuerData(jwtTokenFactory.getServiceIpPort(), DEV_TID));
            var db = tenantDataSourceJpaRepository.save(DEV_DB);
            var ds = new TenantDataSourceUserData(
                    db.getId(),
                    "user",
                    "mysql"
            );
            var dbUser = tenantDataSourceUserJpaRepository.save(ds);
        }
    }

}
