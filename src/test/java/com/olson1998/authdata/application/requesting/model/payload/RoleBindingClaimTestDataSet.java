package com.olson1998.authdata.application.requesting.model.payload;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authdata.application.datasource.entity.AuthorityTestDataSet.TEST_AUTHORITY_1_ID;
import static com.olson1998.authdata.application.datasource.entity.AuthorityTestDataSet.TEST_AUTHORITY_2_ID;
import static com.olson1998.authdata.application.datasource.entity.RoleTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RoleBindingClaimTestDataSet {

    public static final RoleBindingForm TEST_ROLE_BINDING_CLAIM_1 = new RoleBindingForm(
            TEST_ROLE_1_ID,
            TEST_AUTHORITY_1_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_CLAIM_2 = new RoleBindingForm(
            TEST_ROLE_1_ID,
            TEST_AUTHORITY_2_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_CLAIM_3 = new RoleBindingForm(
            TEST_ROLE_2_ID,
            TEST_AUTHORITY_1_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_CLAIM_4 = new RoleBindingForm(
            TEST_ROLE_2_ID,
            TEST_AUTHORITY_2_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_CLAIM_5 = new RoleBindingForm(
            TEST_ROLE_3_ID,
            TEST_AUTHORITY_1_ID
    );

    public static final RoleBindingForm TEST_ROLE_BINDING_CLAIM_6 = new RoleBindingForm(
            TEST_ROLE_3_ID,
            TEST_AUTHORITY_2_ID
    );
}
