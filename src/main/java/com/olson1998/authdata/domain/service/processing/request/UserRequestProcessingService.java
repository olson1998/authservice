package com.olson1998.authdata.domain.service.processing.request;

import com.olson1998.authdata.domain.model.exception.data.NoUserDeletedException;
import com.olson1998.authdata.domain.model.exception.processing.UserPolicyViolationException;
import com.olson1998.authdata.domain.model.processing.report.DomainUserDeletingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainUserMembershipDeletingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainUserMembershipSavingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainUserSavingReport;
import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authdata.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.UserMembershipDataSourceRepository;
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
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.DELETE;
import static com.olson1998.authdata.domain.service.processing.request.ProcessingRequestLogger.RequestType.SAVE;

@Slf4j
@RequiredArgsConstructor
public class UserRequestProcessingService implements UserRequestProcessor {

    private final RoleRequestProcessor roleRequestProcessor;

    private final UserDataSourceRepository userDataSourceRepository;

    private final UserMembershipDataSourceRepository userMembershipDataSourceRepository;

    @Override
    public UserSavingReport saveUser(@NonNull UserSavingRequest request) {
        ProcessingRequestLogger.log(log, request, SAVE, User.class);
        var details = request.getUserDetails();
        var membershipClaims = request.getMembershipClaims();
        var user = userDataSourceRepository.saveUser(details);
        var userId = user.getId();
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
        var deletedPrivateRolesQty = roleRequestProcessor.deleteUserRoles(request);
        ProcessingRequestLogger.log(log, request, DELETE, User.class);
        var deletedUsers = userDataSourceRepository.deleteUser(userId);
        if(deletedUsers == 0){
            throw new NoUserDeletedException(log, request.getId());
        }
        log.debug(
                "deleted {} memberships and {} private roles of user: '{}'",
                deletedMembershipsQty,
                deletedPrivateRolesQty,
                userId
        );
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
        return new DomainUserMembershipSavingReport(id, request.getUserId(), persistedMemberships);
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

    private void checkUserReq(UserSavingRequest request) throws RollbackRequiredException {
        var id = request.getId();
        var username = request.getUserDetails().getUsername();
        var pass = request.getUserDetails().getPassword();
        if(username == null){
            throw new UserPolicyViolationException(id, 0);
        }
        if(pass == null){
            throw new UserPolicyViolationException(id, 1);
        }
        if(username.length() < 8){
            throw new UserPolicyViolationException(id, 2);
        }
        if(pass.length() < 6){
            throw new UserPolicyViolationException(id, 3);
        }
    }
}
