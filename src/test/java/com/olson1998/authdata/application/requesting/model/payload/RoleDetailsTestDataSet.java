package com.olson1998.authdata.application.requesting.model.payload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authdata.application.datasource.entity.RoleTestDataSet.*;
import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.TEST_USER_ID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RoleDetailsTestDataSet {

    public static RoleDetailsForm TEST_ROLE_DETAILS_FORM_1 = new RoleDetailsForm(
            TEST_ROLE_1_NAME,
            TEST_ROLE_1_SUB,
            TEST_USER_ID,
            null,
            null,
            null,
            null
    );

    public static RoleDetailsForm TEST_ROLE_DETAILS_FORM_2 = new RoleDetailsForm(
            TEST_ROLE_2_NAME,
            TEST_ROLE_2_SUB,
            null,
            TEST_ROLE_2_CONO,
            null,
            null,
            null
    );

    public static RoleDetailsForm TEST_ROLE_DETAILS_FORM_3 = new RoleDetailsForm(
            TEST_ROLE_3_NAME,
            TEST_ROLE_3_SUB,
            null,
            null,
            null,
            TEST_ROLE_3_GROUP,
            null
    );

    public static RoleDetailsForm TEST_ROLE_DETAILS_FORM_4 = new RoleDetailsForm(
            TEST_ROLE_4_NAME,
            TEST_ROLE_4_SUB,
            null,
            null,
            TEST_ROLE_4_REG,
            null,
            null
    );

    public static RoleDetailsForm TEST_ROLE_DETAILS_FORM_5 = new RoleDetailsForm(
            TEST_ROLE_5_NAME,
            TEST_ROLE_5_SUB,
            null,
            null,
            null,
            null,
            TEST_ROLE_5_TEAM
    );
}
