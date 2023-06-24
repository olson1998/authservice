package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.tenant.UserData;
import com.olson1998.authdata.application.datasource.entity.tenant.values.SecretDigest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserTestDataSet {

    public static final long TEST_USER_ID = 1L;

    public static final String TEST_USER_USERNAME = "username";

    public static final SecretDigest TEST_USER_SECRET_DIGEST = SecretDigest.SHA256;

    public static final UserData TEST_USER_DATA = new UserData(
            TEST_USER_ID,
            TEST_USER_USERNAME,
            TEST_USER_SECRET_DIGEST
    );

}
