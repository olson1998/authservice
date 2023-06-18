package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.tenant.RoleBindingData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authdata.application.requesting.model.payload.RoleBindingClaimTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RoleBindingTestDataSet {

    public static final RoleBindingData TEST_ROLE_BINDING_1 =
            new RoleBindingData(TEST_ROLE_BINDING_CLAIM_1);

    public static final RoleBindingData TEST_ROLE_BINDING_2 =
            new RoleBindingData(TEST_ROLE_BINDING_CLAIM_2);

    public static final RoleBindingData TEST_ROLE_BINDING_3 =
            new RoleBindingData(TEST_ROLE_BINDING_CLAIM_3);

    public static final RoleBindingData TEST_ROLE_BINDING_4 =
            new RoleBindingData(TEST_ROLE_BINDING_CLAIM_4);

    public static final RoleBindingData TEST_ROLE_BINDING_5 =
            new RoleBindingData(TEST_ROLE_BINDING_CLAIM_5);

    public static final RoleBindingData TEST_ROLE_BINDING_6 =
            new RoleBindingData(TEST_ROLE_BINDING_CLAIM_6);
}
