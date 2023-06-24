package com.olson1998.authdata.application.developer.utils;

import com.olson1998.authdata.application.datasource.LocalThreadTenantDataSource;
import com.olson1998.authdata.application.datasource.entity.global.TenantDataSourceData;
import com.olson1998.authdata.application.datasource.entity.global.TenantDataSourceUserData;
import com.olson1998.authdata.application.datasource.entity.global.TenantSecretData;
import com.olson1998.authdata.application.datasource.entity.global.TrustedIssuerData;
import com.olson1998.authdata.application.datasource.repository.global.spring.TenantDataSourceJpaRepository;
import com.olson1998.authdata.application.datasource.repository.global.spring.TenantDataSourceUserJpaRepository;
import com.olson1998.authdata.application.datasource.repository.global.spring.TenantSecretJpaRepository;
import com.olson1998.authdata.application.datasource.repository.global.spring.TrustedIssuerDataJpaRepository;
import com.olson1998.authdata.application.requesting.model.payload.RoleBindingForm;
import com.olson1998.authdata.domain.port.processing.datasource.TenantSqlDataSourceRepository;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.olson1998.authdata.application.datasource.entity.global.values.SqlDataSource.MARIA_DB;
import static com.olson1998.authdata.application.datasource.entity.tenant.values.JwtAlgorithm.HMAC256;
import static com.olson1998.authdata.application.developer.utils.SampleDeveloperData.SAMPLE_USER_SAVE_REQ;
import static com.olson1998.authdata.application.developer.utils.SampleDeveloperData.roleSavingRequest;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Profile("developer")

@RequiredArgsConstructor

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

    private static final TenantDataSourceUserData DEV_DB_USER = new TenantDataSourceUserData(
            DEV_TID,
            "user",
            "mysql"
    );

    private final LocalThreadTenantDataSource localThreadTenantDataSource;

    private final JwtTokenFactory jwtTokenFactory;

    private final UserRequestProcessor userRequestProcessor;

    private final RoleRequestProcessor roleRequestProcessor;

    private final AuthorityRequestProcessor authorityRequestProcessor;

    private final TenantSecretJpaRepository tenantSecretJpaRepository;

    private final TrustedIssuerDataJpaRepository trustedIssuerDataJpaRepository;

    private final TenantDataSourceJpaRepository tenantDataSourceJpaRepository;

    private final TenantDataSourceUserJpaRepository tenantDataSourceUserJpaRepository;

    private final TenantSqlDataSourceRepository tenantSqlDataSourceRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void injectSampleData(){
        injectTestTenant();
        localThreadTenantDataSource.setCurrentThreadTenantDatasource(DEV_TID);
        injectTestAuthData();
        localThreadTenantDataSource.clean();
    }

    @Modifying
    @Transactional(transactionManager = "globalDatasourceTransactionManager")
    public void injectTestTenant(){
        var db = tenantDataSourceJpaRepository.save(DEV_DB);
        var dbUser = tenantDataSourceUserJpaRepository.save(DEV_DB_USER);
        var dataSource = tenantSqlDataSourceRepository.getForTenant(DEV_TID);
        tenantSecretJpaRepository.save(new TenantSecretData(DEV_TID, System.currentTimeMillis(), randomAlphanumeric(10), HMAC256));
        trustedIssuerDataJpaRepository.save(new TrustedIssuerData(jwtTokenFactory.getServiceIpPort(), DEV_TID));
        localThreadTenantDataSource.appendDataSource(DEV_TID, dataSource);
    }

    public void injectTestAuthData(){
        var userSavingReport =userRequestProcessor.saveUser(SAMPLE_USER_SAVE_REQ);
        var roleSavingReport =roleRequestProcessor.saveNewRoles(roleSavingRequest(userSavingReport.getUserId()));
        userRequestProcessor.bindMemberships(SampleDeveloperData.userMembershipSavingRequest(userSavingReport.getUserId()));
        var authoritySavingReport= authorityRequestProcessor.saveAuthorities(SampleDeveloperData.authoritySavingRequest());
        roleRequestProcessor.saveNewRoleBounds(SampleDeveloperData.roleBoundSavingRequest(roleBindingClaims(roleSavingReport, authoritySavingReport)));
    }

    private Set<RoleBindingClaim> roleBindingClaims(RoleSavingReport roleSavingReport, AuthoritySavingReport authoritySavingReport){
        var roleBoundsClaims = new HashSet<RoleBindingClaim>();
        roleSavingReport.getPersistedRolesDetailsMap()
                .keySet()
                .forEach(role ->{
                    authoritySavingReport.getPersistedAuthoritiesDetailsMap()
                            .keySet()
                            .forEach(authorityId ->{
                                roleBoundsClaims.add(new RoleBindingForm(role, authorityId));
                            });
                });
        return roleBoundsClaims;
    }

}
