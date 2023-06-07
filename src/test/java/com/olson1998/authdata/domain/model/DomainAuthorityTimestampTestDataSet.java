package com.olson1998.authdata.domain.model;

import com.olson1998.authdata.domain.model.mapping.entity.DomainAuthorityTimestamp;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;

import static com.olson1998.authdata.application.datasource.entity.AuthorityTestDataSet.*;
import static com.olson1998.authdata.domain.model.CommonTestConst.NOW;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DomainAuthorityTimestampTestDataSet {

    public static final long TEST_AUTHORITY_TIMESTAMP_1_EXP_TIME =
            NOW - Duration.ofMinutes(5).toMillis();

    public static final long TEST_AUTHORITY_TIMESTAMP_2_EXP_TIME =
            NOW - Duration.ofMinutes(2).toMillis();

    public static final long TEST_AUTHORITY_TIMESTAMP_3_EXP_TIME =
            NOW - Duration.ofDays(3).toMillis();

    public static final DomainAuthorityTimestamp TEST_AUTHORITY_TIMESTAMP_1 =
            new DomainAuthorityTimestamp(
                    TEST_AUTHORITY_1_ID,
                    TEST_AUTHORITY_TIMESTAMP_1_EXP_TIME
            );

    public static final DomainAuthorityTimestamp TEST_AUTHORITY_TIMESTAMP_2 =
            new DomainAuthorityTimestamp(
                    TEST_AUTHORITY_2_ID,
                    TEST_AUTHORITY_TIMESTAMP_2_EXP_TIME
            );

    public static final DomainAuthorityTimestamp TEST_AUTHORITY_TIMESTAMP_3 =
            new DomainAuthorityTimestamp(
                    TEST_AUTHORITY_3_ID,
                    TEST_AUTHORITY_TIMESTAMP_3_EXP_TIME
            );

    public static DomainAuthorityTimestamp createAuthorityTimestamp(String authorityId, Duration createAgo){
        return new DomainAuthorityTimestamp(
                authorityId,
                NOW - createAgo.toMillis()
        );
    }
}
