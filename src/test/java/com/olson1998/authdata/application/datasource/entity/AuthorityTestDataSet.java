package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.tenant.AuthorityData;
import com.olson1998.authdata.application.datasource.entity.tenant.values.ExtendedAuthorityTimestampData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authdata.application.datasource.entity.RoleTestDataSet.*;
import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.TEST_USER_ID;
import static com.olson1998.authdata.domain.model.CommonTestConst.NOW;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuthorityTestDataSet {

    public static String TEST_AUTHORITY_1_ID;

    public static String TEST_AUTHORITY_2_ID;

    public static String TEST_AUTHORITY_3_ID;

    public static final String TEST_AUTHORITY_1_NAME = "test-role-1";

    public static final String TEST_AUTHORITY_2_NAME = "test-role-2";

    public static final String TEST_AUTHORITY_3_NAME = "test-role-3";

    public static final AuthorityData TEST_AUTHORITY_DATA_1 = createTestAuthority(
            1,
            AuthorityData.builder()
                    .authorityName(TEST_AUTHORITY_1_NAME)
    );

    public static final AuthorityData TEST_AUTHORITY_DATA_2 = createTestAuthority(
            2,
            AuthorityData.builder()
                    .authorityName(TEST_AUTHORITY_2_NAME)
    );

    public static final AuthorityData TEST_AUTHORITY_DATA_3 = createTestAuthority(
            3,
            AuthorityData.builder()
                    .authorityName(TEST_AUTHORITY_3_NAME)
    );

    public static final ExtendedAuthorityTimestampData EXTENDED_AUTHORITY_TIMESTAMP_DATA_1 = new ExtendedAuthorityTimestampData(
            TEST_USER_ID,
            TEST_ROLE_1_ID,
            TEST_AUTHORITY_1_ID,
            NOW,
            null
    );

    public static final ExtendedAuthorityTimestampData EXTENDED_AUTHORITY_TIMESTAMP_DATA_2 = new ExtendedAuthorityTimestampData(
            TEST_USER_ID,
            TEST_ROLE_1_ID,
            TEST_AUTHORITY_2_ID,
            NOW,
            null
    );

    public static final ExtendedAuthorityTimestampData EXTENDED_AUTHORITY_TIMESTAMP_DATA_3 = new ExtendedAuthorityTimestampData(
            TEST_USER_ID,
            TEST_ROLE_1_ID,
            TEST_AUTHORITY_3_ID,
            NOW,
            null
    );

    private static AuthorityData createTestAuthority(int roleNumber, AuthorityData.AuthorityDataBuilder builder){
        var auth = builder.build();
        auth.generateId();
        var id  = auth.getId();
        switch (roleNumber){
            case 1 -> TEST_AUTHORITY_1_ID = id;
            case 2 -> TEST_AUTHORITY_2_ID = id;
            case 3 -> TEST_AUTHORITY_3_ID = id;
        }
        return auth;
    }
}
