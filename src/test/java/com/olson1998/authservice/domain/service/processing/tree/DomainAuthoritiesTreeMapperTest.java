package com.olson1998.authservice.domain.service.processing.tree;

import com.olson1998.authservice.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authservice.domain.port.processing.tree.stereotype.AuthoritiesTree;
import com.olson1998.authservice.domain.port.processing.tree.stereotype.AuthorityTimestamp;
import com.olson1998.authservice.domain.port.processing.tree.stereotype.RoleTimestamp;
import com.olson1998.authservice.domain.service.processing.tree.DomainAuthoritiesTreeMapper;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.olson1998.authservice.application.datasource.entity.AuthorityTestDataSet.*;
import static com.olson1998.authservice.application.datasource.entity.RoleTestDataSet.TEST_ROLE_1_ID;
import static com.olson1998.authservice.application.datasource.entity.UserTestDataSet.TEST_USER_DATA;
import static com.olson1998.authservice.application.datasource.entity.UserTestDataSet.TEST_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;

class DomainAuthoritiesTreeMapperTest {

    private static final DomainAuthoritiesTreeMapper DOMAIN_AUTHORITIES_TREE_MAPPER =
            new DomainAuthoritiesTreeMapper();

    private static final Set<ExtendedAuthorityTimestamp> EXTENDED_AUTHORITY_TIMESTAMP_SET = Stream.of(
            EXTENDED_AUTHORITY_TIMESTAMP_DATA_1,
            EXTENDED_AUTHORITY_TIMESTAMP_DATA_2,
            EXTENDED_AUTHORITY_TIMESTAMP_DATA_3
    ).collect(Collectors.toUnmodifiableSet());

    public static final AuthoritiesTree TEST_AUTHORITY_TREE = DOMAIN_AUTHORITIES_TREE_MAPPER.map(
            TEST_USER_DATA,
            EXTENDED_AUTHORITY_TIMESTAMP_SET
    );

    @Test
    void shouldMapTreeForGivenUserId(){
        assertThat(TEST_AUTHORITY_TREE.getUserId()).isEqualTo(TEST_USER_ID);
    }

    @Test
    void shouldMapRolesByRoleIds(){
        var expectedRolesId = Set.of(TEST_ROLE_1_ID);
        var rolesId = getRoleIds();
        assertThat(rolesId).containsExactlyElementsOf(expectedRolesId);
    }

    @Test
    void shouldCorrectlyMapRoleTimestampsWithAuthoritiesTimestamps(){
       var authoritiesTimestampsMap = getAuthoritiesTimestampsMap();
       var authoritiesTimestamps=  authoritiesTimestampsMap.get(TEST_ROLE_1_ID).stream()
               .map(AuthorityTimestamp::getId)
               .toList();
       assertThat(authoritiesTimestamps).containsExactlyInAnyOrder(
               TEST_AUTHORITY_1_ID,
               TEST_AUTHORITY_2_ID,
               TEST_AUTHORITY_3_ID
       );
    }

    private Set<String> getRoleIds(){
        return TEST_AUTHORITY_TREE.getRoleTimestamps().stream()
                .map(RoleTimestamp::getId)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Map<String, Set<AuthorityTimestamp>> getAuthoritiesTimestampsMap(){
        var rolesTimestampsAuthoritiesTimestamps = new HashMap<String, Set<AuthorityTimestamp>>();
        TEST_AUTHORITY_TREE.getRoleTimestamps().forEach(roleTimestamp -> {
            var id = roleTimestamp.getId();
            var authoritiesTmp = roleTimestamp.getAuthoritiesTimestamps();
            rolesTimestampsAuthoritiesTimestamps.put(id, authoritiesTmp);
        });
        return rolesTimestampsAuthoritiesTimestamps;
    }
}
