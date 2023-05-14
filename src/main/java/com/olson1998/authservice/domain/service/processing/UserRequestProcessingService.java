package com.olson1998.authservice.domain.service.processing;

import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authservice.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authservice.domain.port.processing.repository.UserRequestProcessor;
import com.olson1998.authservice.domain.port.request.entity.UserMembershipClaim;
import com.olson1998.authservice.domain.port.request.model.UserSavingRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class UserRequestProcessingService implements UserRequestProcessor {

    private final UserDataSourceRepository userDataSourceRepository;

    private final UserMembershipDataSourceRepository userMembershipDataSourceRepository;

    @Override
    public User saveUser(@NonNull UserSavingRequest request) {
        var details = request.getUserDetails();
        var username = details.getUsername();
        var membershipClaims = request.getMembershipClaims();
        log.debug("saving user: '{}'", username);
        var user = userDataSourceRepository.saveUser(details);
        var userId = user.getId();
        updateMembershipClaimsWithUserId(membershipClaims, userId);
        var size = membershipClaims.size();
        log.debug("binding {} memberships of user: '{}'", size, username);
        userMembershipDataSourceRepository.saveUserMemberships(membershipClaims);
        log.debug("successfully saved user: '{}'", username);
        return user;
    }

    private void updateMembershipClaimsWithUserId(Set<UserMembershipClaim> membershipClaims, long userId){
        membershipClaims.forEach(claim ->{
            claim.setUserId(userId);
        });
    }
}
