package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.application.requesting.model.payload.AuthorityDetailsForm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthorityDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

import static com.olson1998.authdata.application.datasource.entity.AuthorityTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.AuthorityDetailsTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuthorityRequestDataSet {

    public static final UUID TEST_AUTHORITY_SAVING_REQUEST_ID = UUID.randomUUID();

    public static final UUID TEST_AUTHORITY_DELETING_REQUEST_ID = UUID.randomUUID();

    public static final Set<AuthorityDetails> TEST_AUTHORITY_SAVING_REQUEST_AUTHORITY_DETAILS_SET = Set.of(
            TEST_AUTHORITY_DETAILS_FORM_1,
            TEST_AUTHORITY_DETAILS_FORM_2,
            TEST_AUTHORITY_DETAILS_FORM_3
    );

    public static final Set<String> TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS = Set.of(
            TEST_AUTHORITY_1_ID,
            TEST_AUTHORITY_2_ID,
            TEST_AUTHORITY_3_ID
    );

    public static final AuthoritySavingRequest TEST_AUTHORITY_SAVING_REQUEST = new AuthoritySavingRequest() {
        @Override
        public Set<AuthorityDetails> getAuthoritiesDetails() {
            return TEST_AUTHORITY_SAVING_REQUEST_AUTHORITY_DETAILS_SET;
        }

        @Override
        public UUID getId() {
            return TEST_AUTHORITY_SAVING_REQUEST_ID;
        }
    };

    public static final AuthorityDeletingRequest TEST_AUTHORITY_DELETING_REQUEST = new AuthorityDeletingRequest() {
        @Override
        public Set<String> getAuthoritiesIds() {
            return TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS;
        }

        @Override
        public UUID getId() {
            return TEST_AUTHORITY_DELETING_REQUEST_ID;
        }
    };
}
