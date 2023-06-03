package com.olson1998.authservice.domain.service.processing.tree;

import com.olson1998.authservice.domain.model.exception.processing.MismatchAuthoritiesTreeUserIdException;
import com.olson1998.authservice.domain.port.processing.tree.stereotype.AuthoritiesTree;
import com.olson1998.authservice.domain.port.processing.tree.stereotype.RoleTimestamp;
import com.olson1998.authservice.domain.service.processing.tree.DomainAuthoritiesTreeComparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.olson1998.authservice.application.datasource.entity.UserTestDataSet.TEST_USER_ID;
import static com.olson1998.authservice.domain.model.DomainRoleTimestampTestDataSet.TEST_DOMAIN_ROLE_TIMESTAMP;
import static com.olson1998.authservice.domain.model.DomainRoleTimestampTestDataSet.TEST_UPDATED_DOMAIN_ROLE_TIMESTAMP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DomainAuthoritiesComparatorTest {

    private static final DomainAuthoritiesTreeComparator DOMAIN_AUTHORITIES_TREE_COMPARATOR =
            new DomainAuthoritiesTreeComparator();

    private static final Set<RoleTimestamp> TEST_AUTHORITIES_TREE_ROLE_TIMESTAMPS = Set.of(
            TEST_DOMAIN_ROLE_TIMESTAMP
    );

    private static final Set<RoleTimestamp> TEST_UPDATED_AUTHORITIES_TREE_ROLE_TIMESTAMPS = Set.of(
            TEST_UPDATED_DOMAIN_ROLE_TIMESTAMP
    );

    private static final long DIFFERENT_USER_ID = 2L;

    @Mock
    private AuthoritiesTree authoritiesTree1;

    @Mock
    private AuthoritiesTree authoritiesTree2;

    @Test
    void shouldReturnEmptySetIfAuthoritiesTreesRoleTimestampsAreEqual(){
        given(authoritiesTree1.getRoleTimestamps())
                .willReturn(TEST_AUTHORITIES_TREE_ROLE_TIMESTAMPS);
        given(authoritiesTree1.getUserId())
                .willReturn(TEST_USER_ID);
        given(authoritiesTree2.getRoleTimestamps())
                .willReturn(TEST_AUTHORITIES_TREE_ROLE_TIMESTAMPS);
        given(authoritiesTree2.getUserId())
                .willReturn(TEST_USER_ID);

        var differentRolesId = DOMAIN_AUTHORITIES_TREE_COMPARATOR.compare(authoritiesTree1, authoritiesTree2);

        assertThat(differentRolesId).isEmpty();
    }

    @Test
    void shouldReturnSetOfDifferentRolesIds(){
        given(authoritiesTree1.getRoleTimestamps())
                .willReturn(TEST_UPDATED_AUTHORITIES_TREE_ROLE_TIMESTAMPS);
        given(authoritiesTree1.getUserId())
                .willReturn(TEST_USER_ID);
        given(authoritiesTree2.getRoleTimestamps())
                .willReturn(TEST_AUTHORITIES_TREE_ROLE_TIMESTAMPS);
        given(authoritiesTree2.getUserId())
                .willReturn(TEST_USER_ID);

        var differentRolesId = DOMAIN_AUTHORITIES_TREE_COMPARATOR.compare(authoritiesTree1, authoritiesTree2);

        assertThat(differentRolesId).containsExactly(TEST_UPDATED_DOMAIN_ROLE_TIMESTAMP.getId());
    }

    @Test
    void shouldThrowMismatchUserIdExceptionIfAuthoritiesTreeHasDifferentUserId(){
        assertThat(TEST_USER_ID).isNotEqualTo(DIFFERENT_USER_ID);

        given(authoritiesTree1.getUserId())
                .willReturn(TEST_USER_ID);
        given(authoritiesTree2.getUserId())
                .willReturn(DIFFERENT_USER_ID);

        assertThatExceptionOfType(MismatchAuthoritiesTreeUserIdException.class).isThrownBy(()->{
           DOMAIN_AUTHORITIES_TREE_COMPARATOR.compare(authoritiesTree1, authoritiesTree2);
        });
    }
}
