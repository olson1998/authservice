package com.olson1998.authdata.application.datasource.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.TEST_USER_SECRET_DIGEST;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserSecretDataSet {

    public static final String TEST_USER_PASSWORD =
            "supersecretpass";

    public static final byte[] TEST_ENC_USER_PASSWORD_BYTES =
            TEST_USER_SECRET_DIGEST.encrypt(TEST_USER_PASSWORD);
}
