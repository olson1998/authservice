package com.olson1998.authdata.domain.service.processing.request;

import com.olson1998.authdata.domain.model.exception.data.NoUserDeletedException;
import com.olson1998.authdata.domain.model.exception.processing.UserPolicyViolationException;
import com.olson1998.authdata.domain.model.processing.report.DomainUserDeletingReport;
import com.olson1998.authdata.domain.model.processing.report.DomainUserSavingReport;
import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authdata.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserSavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserSavingRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

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
        checkUserReq(request);
        ProcessingRequestLogger.log(log, request, SAVE, User.class);
        var details = request.getUserDetails();
        var membershipClaims = request.getMembershipClaims();
        log.trace("saving new user...");
        var user = userDataSourceRepository.saveUser(details);
        var userId = user.getId();
        if(request.getMembershipClaims() != null){
            updateMembershipClaimsWithUserId(membershipClaims, userId);
            var size = membershipClaims.size();
            log.debug("binding {} memberships of user: '{}'", size, userId);
            userMembershipDataSourceRepository.saveUserMemberships(membershipClaims);
        }else {
            log.trace("no membership claim to bind for user: '{}'", userId);
        }
        log.debug("successfully saved user: '{}'", userId);
        return new DomainUserSavingReport(
                request.getId(),
                user
        );
    }

    @Override
    public UserDeletingReport deleteUser(@NonNull UserDeletingRequest request) {
        var userId = request.getUserId();
        ProcessingRequestLogger.log(log, request, DELETE, UserMembershipClaim.class);
        var deletedMembershipsQty = userMembershipDataSourceRepository.deleteUserMembership(userId);
        var deletedPrivateRolesQty = roleRequestProcessor.deleteUserRoles(request);
        ProcessingRequestLogger.log(log, request, DELETE, User.class);
        var deletedUsers = userDataSourceRepository.deleteUser(userId);
        if(deletedUsers == 0){
            throw new NoUserDeletedException(request.getId());
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

    private void updateMembershipClaimsWithUserId(Set<UserMembershipClaim> membershipClaims, long userId){
        membershipClaims.forEach(claim -> claim.setUserId(userId));
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
