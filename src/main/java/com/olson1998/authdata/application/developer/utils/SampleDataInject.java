package com.olson1998.authdata.application.developer.utils;

import com.olson1998.authdata.application.datasource.entity.TenantSecretData;
import com.olson1998.authdata.application.datasource.entity.TrustedIssuerData;
import com.olson1998.authdata.application.datasource.repository.jpa.TenantSecretJpaRepository;
import com.olson1998.authdata.application.datasource.repository.jpa.TrustedIssuerDataJpaRepository;
import com.olson1998.authdata.application.requesting.model.payload.RoleBindingForm;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static com.olson1998.authdata.application.datasource.entity.values.JwtAlgorithm.HMAC256;
import static com.olson1998.authdata.application.developer.utils.SampleDeveloperData.SAMPLE_USER_SAVE_REQ;
import static com.olson1998.authdata.application.developer.utils.SampleDeveloperData.roleSavingRequest;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Profile("developer")

@RequiredArgsConstructor

@Component
public class SampleDataInject {

    private final JwtTokenFactory jwtTokenFactory;

    private final UserRequestProcessor userRequestProcessor;

    private final RoleRequestProcessor roleRequestProcessor;

    private final AuthorityRequestProcessor authorityRequestProcessor;

    private final TenantSecretJpaRepository tenantSecretJpaRepository;

    private final TrustedIssuerDataJpaRepository trustedIssuerDataJpaRepository;

    @EventListener(ApplicationStartedEvent.class)
    public void injectSampleData(){
        var tid = "AUTHDATA_TST";
        tenantSecretJpaRepository.save(new TenantSecretData(tid, System.currentTimeMillis(), randomAlphanumeric(10), HMAC256));
        trustedIssuerDataJpaRepository.save(new TrustedIssuerData(jwtTokenFactory.getServiceIpPort(), tid));
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
