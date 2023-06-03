package com.olson1998.authservice.application.requesting.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

import static com.olson1998.authservice.application.datasource.entity.UserTestDataSet.*;
import static com.olson1998.authservice.application.requesting.model.payload.UserMembershipTestDataSet.TEST_USER_MEMBERSHIP_FORM;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class UserRequestDataSet {

    public static final UUID TEST_USER_SAVING_REQUEST_ID = UUID.randomUUID();

    public static final UUID TEST_USER_DELETING_REQUEST_ID = UUID.randomUUID();

    public static final UserSavingAdapterRequest TEST_USER_SAVING_REQUEST = new UserSavingAdapterRequest(
            TEST_USER_SAVING_REQUEST_ID,
            TEST_USER_USERNAME,
            TEST_USER_PASSWORD,
            TEST_USER_SECRET_DIGEST,
            null
    );

    public static final UserSavingAdapterRequest TEST_USER_SAVING_REQUEST_WITH_MEMBERSHIP_CLAIM = new UserSavingAdapterRequest(
            TEST_USER_SAVING_REQUEST_ID,
            TEST_USER_USERNAME,
            TEST_USER_PASSWORD,
            TEST_USER_SECRET_DIGEST,
            Set.of(TEST_USER_MEMBERSHIP_FORM)
    );

    public static final UserDeletingAdapterRequest TEST_USER_DELETING_REQUEST = new UserDeletingAdapterRequest(
            TEST_USER_DELETING_REQUEST_ID,
            TEST_USER_ID
    );
}
