package com.olson1998.authdata.application.requesting.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static com.olson1998.authdata.application.datasource.entity.RoleTestDataSet.TEST_ROLE_1_ID;
import static com.olson1998.authdata.application.requesting.model.payload.AuthorityDetailsTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.RoleBindingClaimTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.RoleDetailsTestDataSet.*;
import static java.util.Map.entry;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RoleRequestDataSet {

    public static final UUID TEST_ROLE_SAVING_REQUEST_ID = UUID.randomUUID();

    public static final UUID TEST_ROLE_BINDING_REQUEST_ID = UUID.randomUUID();

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

    public static final RoleBoundSavingAdapterRequest TEST_ROLE_BINDING_REQUEST = new RoleBoundSavingAdapterRequest(
            TEST_ROLE_BINDING_REQUEST_ID,
            Set.of(
                    TEST_ROLE_BINDING_CLAIM_1,
                    TEST_ROLE_BINDING_CLAIM_2,
                    TEST_ROLE_BINDING_CLAIM_3,
                    TEST_ROLE_BINDING_CLAIM_4,
                    TEST_ROLE_BINDING_CLAIM_5,
                    TEST_ROLE_BINDING_CLAIM_6
            ),
            null
    );

    public static final RoleBoundSavingAdapterRequest TEST_ROLE_BINDING_REQUEST_WITH_SAVE_CLAIM = new RoleBoundSavingAdapterRequest(
            TEST_ROLE_BINDING_REQUEST_ID,
            Set.of(
                    TEST_ROLE_BINDING_CLAIM_1,
                    TEST_ROLE_BINDING_CLAIM_2,
                    TEST_ROLE_BINDING_CLAIM_3,
                    TEST_ROLE_BINDING_CLAIM_4,
                    TEST_ROLE_BINDING_CLAIM_5,
                    TEST_ROLE_BINDING_CLAIM_6
            ),
            Map.ofEntries(
                    entry(TEST_ROLE_1_ID, Set.of(TEST_AUTHORITY_DETAILS_FORM_1, TEST_AUTHORITY_DETAILS_FORM_2, TEST_AUTHORITY_DETAILS_FORM_3))
            )
    );
}
