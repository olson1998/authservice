package com.olson1998.authdata.application.requesting.model.payload;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authdata.application.datasource.entity.UserMembershipTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserMembershipClaimTestDataSet {

    public static final UserMembershipClaim TEST_USER_COMPANY_MEMBERSHIP_CLAIM = new UserMembershipForm(
            TEST_COMPANY_NUMBER,
            null,
            null,
            null
    );

    public static final UserMembershipClaim TEST_USER_REGION_MEMBERSHIP_CLAIM = new UserMembershipForm(
            null,
            TEST_REGION_ID,
            null,
            null
    );

    public static final UserMembershipClaim TEST_USER_GROUP_MEMBERSHIP_CLAIM = new UserMembershipForm(
            null,
            null,
            TEST_GROUP_ID,
            null
    );

    public static final UserMembershipClaim TEST_USER_TEAM_MEMBERSHIP_CLAIM = new UserMembershipForm(
            null,
            null,
            null,
            TEST_TEAM_ID
    );

}
