package com.olson1998.authdata.domain.service.processing.request;

import com.olson1998.authdata.domain.model.exception.data.DifferentAffectedRowsThanRequired;
import com.olson1998.authdata.domain.model.processing.report.DomainUserDeletingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainUserMembershipDeletingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainUserMembershipSavingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainUserSavingReport;
import com.olson1998.authdata.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.UserSecretDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.data.stereotype.UserMembership;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipBindReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserSavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import com.olson1998.authdata.domain.port.security.repository.TenantSecretProvider;
import com.olson1998.authdata.domain.port.security.repository.UserPasswordEnigma;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.*;

import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.DELETE;
import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.SAVE;

@Slf4j
@RequiredArgsConstructor
public class UserRequestProcessingService implements UserRequestProcessor {

    private final TenantSecretProvider tenantSecretProvider;

    private final UserPasswordEnigma userPasswordEnigma;

    private final RoleRequestProcessor roleRequestProcessor;

    private final UserDataSourceRepository userDataSourceRepository;

    private final UserSecretDataSourceRepository userSecretDataSourceRepository;

    private final UserMembershipDataSourceRepository userMembershipDataSourceRepository;

    @Override
    public UserSavingReport saveUser(@NonNull UserSavingRequest request) {
        ProcessingRequestLogger.log(log, request, SAVE, User.class);
        var timestamp = System.currentTimeMillis();
        var tid = request.getTenantId();
        var details = request.getUserDetails();
        var password = details.getPassword();
        var membershipClaims = request.getMembershipClaims();
        var secret = tenantSecretProvider.getTenantSecret(tid)
                .orElseThrow();
        var user = userDataSourceRepository.saveUser(details, timestamp);
        var userId = user.getId();
        var encryptedPass = userPasswordEnigma.getEncryptedPassword(secret.getPasswordEncryptionType(), password);
        var passExpTime = timestamp + details.getPasswordExpDuration().toMillis();
        userSecretDataSourceRepository.saveUserSecret(userId, encryptedPass, passExpTime);
        roleRequestProcessor.saveNewUserPrivateRole(userId);
        if(request.getMembershipClaims() != null){
            var size = membershipClaims.size();
            userMembershipDataSourceRepository.saveUserMemberships(userId, membershipClaims);
        }
        return new DomainUserSavingReport(
                request.getId(),
                user
        );
    }

    @Override
    public UserDeletingReport deleteUser(@NonNull UserDeletingRequest request) {
        var userId = request.getUserId();
        ProcessingRequestLogger.log(log, request, DELETE, UserMembershipClaim.class);
        var deletedMembershipsQty = userMembershipDataSourceRepository.deleteAllUserMemberships(userId);
        var deletedBytes = userSecretDataSourceRepository.deleteUserSecret(userId);
        var deletedPrivateRolesQty = roleRequestProcessor.deleteUserRoles(request);
        ProcessingRequestLogger.log(log, request, DELETE, User.class);
        var deletedUsers = userDataSourceRepository.deleteUser(userId);
        if(deletedUsers == 0){
            throw new DifferentAffectedRowsThanRequired(log, request.getId(), 1, 0);
        }
        return new DomainUserDeletingReport(
                request.getId(),
                userId,
                deletedPrivateRolesQty,
                deletedMembershipsQty
        );
    }

    @Override
    public UserMembershipBindReport bindMemberships(@NonNull UserMembershipSavingRequest request) {
        ProcessingRequestLogger.log(log, request, SAVE, UserMembership.class);
        var id = request.getId();
        var userId = request.getUserId();
        var claims = request.getUserMembershipClaims();
        var persistedMemberships = userMembershipDataSourceRepository.saveUserMemberships(userId, claims);
        var persistedMembershipsMap = mapUserMembershipBindings(claims, persistedMemberships);
        return new DomainUserMembershipSavingReport(id, request.getUserId(), persistedMembershipsMap);
    }

    @Override
    public UserMembershipDeletingReport deleteMemberships(@NonNull UserMembershipDeletingRequest request) {
        ProcessingRequestLogger.log(log, request, DELETE, UserMembership.class);
        var userId = request.getUserId();
        var reportBuilder = DomainUserMembershipDeletingReport.builder();
        reportBuilder.requestId(request.getId());
        reportBuilder.userId(userId);
        if(!request.getRegionMemberships().isEmpty()){
            var regionsIds= request.getRegionMemberships();
            var deleted = userMembershipDataSourceRepository.deleteUserRegionMembership(userId, regionsIds);
            reportBuilder.deletedRegionMemberships(deleted);
        }
        if(!request.getGroupMemberships().isEmpty()){
            var groupIds = request.getGroupMemberships();
            var deleted = userMembershipDataSourceRepository.deleteUserGroupMembership(userId, groupIds);
            reportBuilder.deletedGroupMemberships(deleted);
        }
        if(!request.getTeamMemberships().isEmpty()){
            var teamIds = request.getTeamMemberships();
            var deleted = userMembershipDataSourceRepository.deleteUserTeamMembership(userId, teamIds);
            reportBuilder.deletedTeamMemberships(deleted);
        }
        return reportBuilder.build();
    }

    private Map<String, UserMembershipClaim> mapUserMembershipBindings(Set<UserMembershipClaim> userMembershipClaims, List<UserMembership> userMemberships){
        var savedMemberships = new HashMap<String, UserMembershipClaim>();
        userMembershipClaims.forEach(userMembershipClaim -> {
            userMemberships.forEach(userMembership -> {
                if(userMembershipClaim.isMatching(userMembership)){
                    var id = userMembership.getId();
                    savedMemberships.put(id, userMembershipClaim);
                }
            });
        });
        return savedMemberships;
    }

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return userDataSourceRepository.getAuthUserByUsername(id)
                .orElseThrow();
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return findById(clientId);
    }
}
