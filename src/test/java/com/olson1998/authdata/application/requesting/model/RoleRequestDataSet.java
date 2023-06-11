package com.olson1998.authdata.application.requesting.model;

import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
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

    public static final Set<RoleDetails> TEST_ROLE_SAVING_REQUEST_DETAILS = Set.of(
                    TEST_ROLE_DETAILS_FORM_1,
                    TEST_ROLE_DETAILS_FORM_2,
                    TEST_ROLE_DETAILS_FORM_3,
                    TEST_ROLE_DETAILS_FORM_4,
                    TEST_ROLE_DETAILS_FORM_5
            );

    public static final Set<RoleBindingClaim> TEST_ROLE_BOUNDS_REQUEST_CLAIMS = Set.of(
            TEST_ROLE_BINDING_CLAIM_1,
            TEST_ROLE_BINDING_CLAIM_2,
            TEST_ROLE_BINDING_CLAIM_3,
            TEST_ROLE_BINDING_CLAIM_4,
            TEST_ROLE_BINDING_CLAIM_5,
            TEST_ROLE_BINDING_CLAIM_6
    );

    public static final Map<String, Set<AuthorityDetails>> TEST_ROLE_BOUNDS_SAVING_REQUEST_WITH_AUTHORITIES_SAVING_CLAIM = Map.ofEntries(
            entry(TEST_ROLE_1_ID, Set.of(TEST_AUTHORITY_DETAILS_FORM_1, TEST_AUTHORITY_DETAILS_FORM_2, TEST_AUTHORITY_DETAILS_FORM_3))
    );

    public static final RoleSavingRequest TEST_ROLE_SAVING_REQUEST = new RoleSavingRequest() {
        @Override
        public Set<RoleDetails> getDetails() {
            return TEST_ROLE_SAVING_REQUEST_DETAILS;
        }

        @Override
        public UUID getId() {
            return TEST_ROLE_SAVING_REQUEST_ID;
        }
    };

    public static final RoleBoundSavingRequest TEST_ROLE_BINDING_REQUEST = new RoleBoundSavingRequest() {
        @Override
        public Set<RoleBindingClaim> getRolesBindingsClaims() {
            return TEST_ROLE_BOUNDS_REQUEST_CLAIMS;
        }

        @Override
        public Map<String, Set<AuthorityDetails>> getRoleIdAuthoritySavingRequestMap() {
            return Collections.emptyMap();
        }

        @Override
        public UUID getId() {
            return TEST_ROLE_BINDING_REQUEST_ID;
        }
    };

    public static final RoleBoundSavingRequest TEST_ROLE_BINDING_REQUEST_WITH_SAVE_CLAIM = new RoleBoundSavingRequest() {
        @Override
        public Set<RoleBindingClaim> getRolesBindingsClaims() {
            return TEST_ROLE_BOUNDS_REQUEST_CLAIMS;
        }

        @Override
        public Map<String, Set<AuthorityDetails>> getRoleIdAuthoritySavingRequestMap() {
            return TEST_ROLE_BOUNDS_SAVING_REQUEST_WITH_AUTHORITIES_SAVING_CLAIM;
        }

        @Override
        public UUID getId() {
            return TEST_ROLE_BINDING_REQUEST_ID;
        }
    };

}
