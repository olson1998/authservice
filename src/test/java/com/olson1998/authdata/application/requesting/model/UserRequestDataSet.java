package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.UserDetailsTestDataSet.TEST_USER_DETAILS;
import static com.olson1998.authdata.application.requesting.model.payload.UserMembershipTestDataSet.TEST_USER_MEMBERSHIP_FORM;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserRequestDataSet {

    public static final UUID TEST_USER_SAVING_REQUEST_ID = UUID.randomUUID();

    public static final UUID TEST_USER_DELETING_REQUEST_ID = UUID.randomUUID();

    public static final Set<UserMembershipClaim> TEST_USER_SAVING_REQUEST_MEMBERSHIP_CLAIMS = Set.of(TEST_USER_MEMBERSHIP_FORM);

    public static final UserSavingRequest TEST_USER_SAVING_REQUEST = new UserSavingRequest() {
        @Override
        public UserDetails getUserDetails() {
            return TEST_USER_DETAILS;
        }

        @Override
        public Set<UserMembershipClaim> getMembershipClaims() {
            return null;
        }

        @Override
        public UUID getId() {
            return TEST_USER_SAVING_REQUEST_ID;
        }
    };

    public static final UserSavingRequest TEST_USER_SAVING_REQUEST_WITH_MEMBERSHIP_CLAIM = new UserSavingRequest() {
        @Override
        public UserDetails getUserDetails() {
            return TEST_USER_DETAILS;
        }

        @Override
        public Set<UserMembershipClaim> getMembershipClaims() {
            return TEST_USER_SAVING_REQUEST_MEMBERSHIP_CLAIMS;
        }

        @Override
        public UUID getId() {
            return TEST_USER_SAVING_REQUEST_ID;
        }
    };

    public static final UserDeletingRequest TEST_USER_DELETING_REQUEST = new UserDeletingRequest() {
        @Override
        public long getUserId() {
            return TEST_USER_ID;
        }

        @Override
        public UUID getId() {
            return TEST_USER_DELETING_REQUEST_ID;
        }
    };
}
