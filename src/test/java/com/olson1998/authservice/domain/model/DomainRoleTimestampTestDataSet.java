package com.olson1998.authservice.domain.model;

import com.olson1998.authservice.domain.model.mapping.entity.DomainRoleTimestamp;
import com.olson1998.authservice.domain.port.processing.tree.stereotype.AuthorityTimestamp;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Set;

import static com.olson1998.authservice.application.datasource.entity.RoleTestDataSet.TEST_ROLE_1_ID;
import static com.olson1998.authservice.domain.model.CommonTestConst.NOW;
import static com.olson1998.authservice.domain.model.DomainAuthorityTimestampTestDataSet.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class DomainRoleTimestampTestDataSet {

    public static final Set<AuthorityTimestamp> TEST_AUTHORITIES_TREE_ROLE_TIMESTAMPS = Set.of(
            TEST_AUTHORITY_TIMESTAMP_1,
            TEST_AUTHORITY_TIMESTAMP_2
    );

    public static final Set<AuthorityTimestamp> TEST_UPDATED_AUTHORITIES_TREE_ROLE_TIMESTAMPS = Set.of(
            TEST_AUTHORITY_TIMESTAMP_1,
            TEST_AUTHORITY_TIMESTAMP_2,
            TEST_AUTHORITY_TIMESTAMP_3
    );

    public static final DomainRoleTimestamp TEST_DOMAIN_ROLE_TIMESTAMP = new DomainRoleTimestamp(
            TEST_ROLE_1_ID,
            NOW,
            TEST_AUTHORITIES_TREE_ROLE_TIMESTAMPS
    );

    public static final DomainRoleTimestamp TEST_UPDATED_DOMAIN_ROLE_TIMESTAMP = new DomainRoleTimestamp(
            TEST_ROLE_1_ID,
            NOW + Duration.ofMinutes(1).toMillis(),
            TEST_UPDATED_AUTHORITIES_TREE_ROLE_TIMESTAMPS
    );
}
