package com.olson1998.authdata.application.requesting.model.payload;

import com.olson1998.authdata.application.datasource.entity.tenant.AuthorityData;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authdata.application.datasource.entity.AuthorityTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuthorityDetailsTestDataSet {

    public static final AuthorityDetailsForm TEST_AUTHORITY_DETAILS_FORM_1 = new AuthorityDetailsForm(
            TEST_AUTHORITY_DATA_1.getAuthorityName(),
            TEST_AUTHORITY_DATA_1.getAuthorityToken(),
            TEST_AUTHORITY_DATA_1.getLevel(),
            TEST_AUTHORITY_DATA_1.getExpiringTime()
    );

    public static final AuthorityDetailsForm TEST_AUTHORITY_DETAILS_FORM_2 = new AuthorityDetailsForm(
            TEST_AUTHORITY_DATA_2.getAuthorityName(),
            TEST_AUTHORITY_DATA_2.getAuthorityToken(),
            TEST_AUTHORITY_DATA_2.getLevel(),
            TEST_AUTHORITY_DATA_2.getExpiringTime()
    );

    public static final AuthorityDetailsForm TEST_AUTHORITY_DETAILS_FORM_3 = new AuthorityDetailsForm(
            TEST_AUTHORITY_DATA_3.getAuthorityName(),
            TEST_AUTHORITY_DATA_3.getAuthorityToken(),
            TEST_AUTHORITY_DATA_3.getLevel(),
            TEST_AUTHORITY_DATA_3.getExpiringTime()
    );

    public static AuthorityData fromAuthorityDetails(AuthorityDetails authorityDetails){
        var authData =new AuthorityData(authorityDetails);
        authData.generateId();
        return authData;
    }
}
