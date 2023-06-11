package com.olson1998.authdata.application.requesting.model.payload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserDetailsTestDataSet {

    public static final UserDetailsForm TEST_USER_DETAILS = new UserDetailsForm(
            TEST_USER_USERNAME,
            TEST_USER_PASSWORD,
            TEST_USER_SECRET_DIGEST
    );
}
