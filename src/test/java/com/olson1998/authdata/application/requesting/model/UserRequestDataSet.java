package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

import static com.olson1998.authdata.application.datasource.entity.UserMembershipTestDataSet.*;
import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.UserDetailsTestDataSet.TEST_USER_DETAILS;
import static com.olson1998.authdata.application.requesting.model.payload.UserMembershipClaimTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserRequestDataSet {

    public static final UUID TEST_USER_SAVING_REQUEST_ID = UUID.randomUUID();

    public static final UUID TEST_USER_DELETING_REQUEST_ID = UUID.randomUUID();

    public static final UUID TEST_USER_MEMBERSHIP_SAVING_REQUEST_ID = UUID.randomUUID();

    public static final UUID TEST_USER_MEMBERSHIP_DELETING_REQUEST_ID = UUID.randomUUID();

    public static final Set<UserMembershipClaim> TEST_MEMBERSHIP_CLAIMS_SET = Set.of(
            TEST_USER_COMPANY_MEMBERSHIP_CLAIM,
            TEST_USER_GROUP_MEMBERSHIP_CLAIM,
            TEST_USER_TEAM_MEMBERSHIP_CLAIM,
            TEST_USER_REGION_MEMBERSHIP_CLAIM
    );

    public static final Set<String> TEST_USER_MEM_DEL_REQ_REGIONAL_MEM_IDS = Set.of(TEST_REGION_ID);

    public static final Set<Long> TEST_USER_MEM_DEL_REQ_GROUP_MEM_IDS = Set.of(TEST_GROUP_ID);

    public static final Set<Long> TEST_USER_MEM_DEL_REQ_COMPANY_MEM_IDS = Set.of(TEST_COMPANY_NUMBER);

    public static final Set<Long> TEST_USER_MEM_DEL_REQ_TEAM_MEM_IDS = Set.of(TEST_TEAM_ID);

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
            return TEST_MEMBERSHIP_CLAIMS_SET;
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

    public static final UserMembershipSavingRequest TEST_USER_MEMBERSHIP_SAVING_REQUEST = new UserMembershipSavingRequest() {
        @Override
        public long getUserId() {
            return TEST_USER_ID;
        }

        @Override
        public Set<UserMembershipClaim> getUserMembershipClaims() {
            return TEST_MEMBERSHIP_CLAIMS_SET;
        }

        @Override
        public UUID getId() {
            return TEST_USER_MEMBERSHIP_SAVING_REQUEST_ID;
        }
    };

    public static final UserMembershipDeletingRequest TEST_USER_MEMBERSHIPS_DELETING_REQUEST = new UserMembershipDeletingRequest() {
        @Override
        public long getUserId() {
            return TEST_USER_ID;
        }

        @Override
        public Set<String> getRegionMemberships() {
            return TEST_USER_MEM_DEL_REQ_REGIONAL_MEM_IDS;
        }

        @Override
        public Set<Long> getGroupMemberships() {
            return TEST_USER_MEM_DEL_REQ_GROUP_MEM_IDS;
        }

        @Override
        public Set<Long> getTeamMemberships() {
            return TEST_USER_MEM_DEL_REQ_TEAM_MEM_IDS;
        }

        @Override
        public UUID getId() {
            return TEST_USER_MEMBERSHIP_DELETING_REQUEST_ID;
        }
    };
}
