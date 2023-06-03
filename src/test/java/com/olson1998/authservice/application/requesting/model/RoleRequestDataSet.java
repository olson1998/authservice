package com.olson1998.authservice.application.requesting.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

import static com.olson1998.authservice.application.requesting.model.payload.RoleDetailsTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RoleRequestDataSet {

    public static final UUID TEST_ROLE_SAVING_REQUEST_ID = UUID.randomUUID();

    public static final RoleSavingAdapterRequest TEST_ROLE_SAVING_REQUEST = new RoleSavingAdapterRequest(
            TEST_ROLE_SAVING_REQUEST_ID,
            Set.of(
                    TEST_ROLE_DETAILS_FORM_1,
                    TEST_ROLE_DETAILS_FORM_2,
                    TEST_ROLE_DETAILS_FORM_3,
                    TEST_ROLE_DETAILS_FORM_4,
                    TEST_ROLE_DETAILS_FORM_5
            )
    );
}
