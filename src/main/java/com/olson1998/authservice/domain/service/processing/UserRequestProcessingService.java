package com.olson1998.authservice.domain.service.processing;

import com.olson1998.authservice.domain.model.exception.data.NoUserDeletedException;
import com.olson1998.authservice.domain.model.exception.request.UserPolicyViolationException;
import com.olson1998.authservice.domain.model.processing.report.SimpleUserDeletingReport;
import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authservice.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authservice.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authservice.domain.port.processing.report.UserDeletingReport;
import com.olson1998.authservice.domain.port.processing.repository.UserRequestProcessor;
import com.olson1998.authservice.domain.port.request.data.UserMembershipClaim;
import com.olson1998.authservice.domain.port.request.stereotype.UserDeletingRequest;
import com.olson1998.authservice.domain.port.request.stereotype.UserSavingRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class UserRequestProcessingService implements UserRequestProcessor {

    private final UserDataSourceRepository userDataSourceRepository;

    private final UserMembershipDataSourceRepository userMembershipDataSourceRepository;

    private final RoleDataSourceRepository roleDataSourceRepository;

    @Override
    public User saveUser(@NonNull UserSavingRequest request) {
        checkUserReq(request);
        var details = request.getUserDetails();
        var membershipClaims = request.getMembershipClaims();
        log.trace("saving new user...");
        var user = userDataSourceRepository.saveUser(details);
        var userId = user.getId();
        updateMembershipClaimsWithUserId(membershipClaims, userId);
        var size = membershipClaims.size();
        log.debug("binding {} memberships of user: '{}'", size, userId);
        userMembershipDataSourceRepository.saveUserMemberships(membershipClaims);
        log.debug("successfully saved user: '{}'", userId);
        return user;
    }

    @Override
    public UserDeletingReport deleteUser(@NonNull UserDeletingRequest request) {
        var userId = request.getUserId();
        var deletedMembershipsQty = userMembershipDataSourceRepository.deleteUserMembership(userId);
        var deletedPrivateRolesQty = roleDataSourceRepository.deleteAllPrivateRolesByUserId(userId);
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
        return new SimpleUserDeletingReport(
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
