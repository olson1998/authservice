package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.tenant.UserMembershipData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.TEST_USER_ID;
import static com.olson1998.authdata.application.requesting.model.payload.UserMembershipClaimTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserMembershipTestDataSet {

    public static final long TEST_COMPANY_NUMBER = 1L;

    public static final String TEST_REGION_ID = "USA/TEST";

    public static final long TEST_GROUP_ID = 1L;

    public static final long TEST_TEAM_ID = 1L;

    public static final UserMembershipData TEST_USER_COMPANY_MEMBERSHIP= new UserMembershipData(
            TEST_USER_ID,
            TEST_USER_COMPANY_MEMBERSHIP_CLAIM
    );

    public static final UserMembershipData TEST_USER_REGION_MEMBERSHIP = new UserMembershipData(
            TEST_USER_ID,
            TEST_USER_REGION_MEMBERSHIP_CLAIM
    );

    public static final UserMembershipData TEST_USER_GROUP_MEMBERSHIP = new UserMembershipData(
            TEST_USER_ID,
            TEST_USER_GROUP_MEMBERSHIP_CLAIM
    );

    public static final UserMembershipData TEST_USER_TEAM_MEMBERSHIP = new UserMembershipData(
            TEST_USER_ID,
            TEST_USER_TEAM_MEMBERSHIP_CLAIM
    );

    public static final String TEST_USER_COMPANY_MEMBERSHIP_ID = Objects.requireNonNull(TEST_USER_COMPANY_MEMBERSHIP.getId());

    public static final String TEST_USER_REGION_MEMBERSHIP_ID = Objects.requireNonNull(TEST_USER_REGION_MEMBERSHIP.getId());

    public static final String TEST_USER_GROUP_MEMBERSHIP_ID = Objects.requireNonNull(TEST_USER_GROUP_MEMBERSHIP.getId());

    public static final String TEST_USER_TEAM_MEMBERSHIP_ID = Objects.requireNonNull(TEST_USER_TEAM_MEMBERSHIP.getId());
}
