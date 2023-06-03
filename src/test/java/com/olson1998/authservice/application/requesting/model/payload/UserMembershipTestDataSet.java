package com.olson1998.authservice.application.requesting.model.payload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authservice.application.datasource.entity.UserMembershipTestDataSet.TEST_COMPANY_NUMBER;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserMembershipTestDataSet {

    public static final UserMembershipForm TEST_USER_MEMBERSHIP_FORM = new UserMembershipForm(
            TEST_COMPANY_NUMBER,
            null,
            null,
            null
    );
}
