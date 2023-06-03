package com.olson1998.authservice.application.requesting.model.payload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authservice.application.datasource.entity.AuthorityTestDataSet.TEST_AUTHORITY_1_ID;
import static com.olson1998.authservice.application.datasource.entity.AuthorityTestDataSet.TEST_AUTHORITY_2_ID;
import static com.olson1998.authservice.application.datasource.entity.RoleTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RoleBindingTestDataSet {

    public static final RoleBindingForm TEST_ROLE_BINDING_1 = new RoleBindingForm(
            TEST_ROLE_1_ID,
            TEST_AUTHORITY_1_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_2 = new RoleBindingForm(
            TEST_ROLE_1_ID,
            TEST_AUTHORITY_2_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_3 = new RoleBindingForm(
            TEST_ROLE_2_ID,
            TEST_AUTHORITY_1_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_4 = new RoleBindingForm(
            TEST_ROLE_2_ID,
            TEST_AUTHORITY_2_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_5 = new RoleBindingForm(
            TEST_ROLE_3_ID,
            TEST_AUTHORITY_1_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_6 = new RoleBindingForm(
            TEST_ROLE_3_ID,
            TEST_AUTHORITY_2_ID
    );
}
