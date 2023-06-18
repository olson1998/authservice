package com.olson1998.authdata.application.datasource.entity;

import com.olson1998.authdata.application.datasource.entity.tenant.RoleData;
import com.olson1998.authdata.application.datasource.entity.tenant.values.RoleSubject;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.TEST_USER_ID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class RoleTestDataSet {

    public static String TEST_ROLE_1_ID;

    public static String TEST_ROLE_2_ID;

    public static String TEST_ROLE_3_ID;

    public static String TEST_ROLE_4_ID;

    public static String TEST_ROLE_5_ID;

    public static final long TEST_ROLE_2_CONO = 1L;

    public static final long TEST_ROLE_3_GROUP = 1L;

    public static final String TEST_ROLE_4_REG = "TEST001";

    public static final long TEST_ROLE_5_TEAM = 1L;

    public static final String TEST_ROLE_1_NAME = "test-private-role";

    public static final String TEST_ROLE_2_NAME = "test-company-role";

    public static final String TEST_ROLE_3_NAME = "test-group-role";

    public static final String TEST_ROLE_4_NAME = "test-region-role";

    public static final String TEST_ROLE_5_NAME = "test-team-role";

    public static final RoleSubject TEST_ROLE_1_SUB = RoleSubject.PRIVATE;

    public static final RoleSubject TEST_ROLE_2_SUB = RoleSubject.COMPANY;

    public static final RoleSubject TEST_ROLE_3_SUB = RoleSubject.GROUP;

    public static final RoleSubject TEST_ROLE_4_SUB = RoleSubject.REGION;

    public static final RoleSubject TEST_ROLE_5_SUB = RoleSubject.TEAM;

    public static final RoleData TEST_ROLE_DATA_1 = createTestRole(
            1,
            RoleData.builder()
                    .name(TEST_ROLE_1_NAME)
                    .subject(TEST_ROLE_1_SUB)
                    .userId(TEST_USER_ID)
    );

    public static final RoleData TEST_ROLE_DATA_2 = createTestRole(
            2,
            RoleData.builder()
                    .name(TEST_ROLE_2_NAME)
                    .subject(TEST_ROLE_2_SUB)
                    .companyNumber(TEST_ROLE_2_CONO)
    );

    public static final RoleData TEST_ROLE_DATA_3 = createTestRole(
            3,
            RoleData.builder()
                    .name(TEST_ROLE_3_NAME)
                    .subject(TEST_ROLE_3_SUB)
                    .groupId(TEST_ROLE_3_GROUP)
    );

    public static final RoleData TEST_ROLE_DATA_4 = createTestRole(
            4,
            RoleData.builder()
                    .name(TEST_ROLE_4_NAME)
                    .subject(TEST_ROLE_4_SUB)
                    .regionId(TEST_ROLE_4_REG)
    );

    public static final RoleData TEST_ROLE_DATA_5 = createTestRole(
            5,
            RoleData.builder()
                    .name(TEST_ROLE_5_NAME)
                    .subject(TEST_ROLE_5_SUB)
                    .teamId(TEST_ROLE_5_TEAM)
    );

    public static RoleData fromRoleDetails(RoleDetails roleDetails){
        var roleData = new RoleData(roleDetails);
        roleData.generateId();
        return roleData;
    }

    private static RoleData createTestRole(int roleNumber, RoleData.RoleDataBuilder builder){
        var role = builder.build();
        role.generateId();
        var id = role.getId();
        switch (roleNumber){
            case 1 -> TEST_ROLE_1_ID = id;
            case 2 -> TEST_ROLE_2_ID = id;
            case 3 -> TEST_ROLE_3_ID = id;
            case 4 -> TEST_ROLE_4_ID = id;
            case 5 -> TEST_ROLE_5_ID = id;
        }
        return role;
    }

}
