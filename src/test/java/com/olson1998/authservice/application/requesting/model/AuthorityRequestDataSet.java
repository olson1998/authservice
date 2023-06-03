package com.olson1998.authservice.application.requesting.model;

import com.olson1998.authservice.application.requesting.entity.AuthorityDetailsForm;
import com.olson1998.authservice.application.requesting.entity.data.AuthoritySavingAdapterRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

import static com.olson1998.authservice.application.requesting.model.payload.AuthorityDetailsTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuthorityRequestDataSet {

    public static final UUID TEST_AUTHORITY_SAVING_REQUEST_ID = UUID.randomUUID();

    public static final Set<AuthorityDetailsForm> TEST_AUTHORITY_SAVING_REQUEST_AUTHORITY_DETAILS_SET = Set.of(
            TEST_AUTHORITY_DETAILS_FORM_1,
            TEST_AUTHORITY_DETAILS_FORM_2,
            TEST_AUTHORITY_DETAILS_FORM_3
    );

    public static final AuthoritySavingAdapterRequest TEST_AUTHORITY_SAVING_REQUEST = new AuthoritySavingAdapterRequest(
            TEST_AUTHORITY_SAVING_REQUEST_ID,
            TEST_AUTHORITY_SAVING_REQUEST_AUTHORITY_DETAILS_SET
    );
}
