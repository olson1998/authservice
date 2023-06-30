package com.olson1998.authdata.domain.service.processing;

import com.olson1998.authdata.domain.model.exception.data.DifferentAffectedRowsThanRequired;
import com.olson1998.authdata.domain.port.data.repository.AuthorityDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.RoleBindingDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Authority;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import com.olson1998.authdata.domain.service.processing.request.AuthorityRequestProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.olson1998.authdata.application.datasource.entity.AuthorityTestDataSet.*;
import static com.olson1998.authdata.application.datasource.entity.RoleTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.AuthorityRequestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.AuthorityDetailsTestDataSet.*;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AuthorityRequestProcessingServiceTest {

    private static final List<Authority> MOCKED_PERSISTED_AUTHORITIES_SET = List.of(
            TEST_AUTHORITY_DATA_1,
            TEST_AUTHORITY_DATA_2,
            TEST_AUTHORITY_DATA_3
    );

    private static final Set<String> MOCKED_BOUNDED_ROLES_IDS = Set.of(
            TEST_ROLE_1_ID,
            TEST_ROLE_2_ID,
            TEST_ROLE_3_ID
    );

    private static final Map<String, AuthorityDetails> EXPECTED_PERSISTED_AUTHORITIES_MAP = Map.ofEntries(
            entry(TEST_AUTHORITY_1_ID, TEST_AUTHORITY_DETAILS_FORM_1),
            entry(TEST_AUTHORITY_2_ID, TEST_AUTHORITY_DETAILS_FORM_2),
            entry(TEST_AUTHORITY_3_ID, TEST_AUTHORITY_DETAILS_FORM_3)
    );

    @Mock
    private AuthorityDataSourceRepository authorityDataSourceRepository;

    @Mock
    private RoleBindingDataSourceRepository roleBindingDataSourceRepository;

    @Mock
    private RoleDataSourceRepository roleDataSourceRepository;

    @InjectMocks
    private AuthorityRequestProcessingService authorityRequestProcessingService;

    @Test
    void shouldSaveAuthoritiesDetails(){
        authorityRequestProcessingService.saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST);

        then(authorityDataSourceRepository).should().saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST_AUTHORITY_DETAILS_SET);
    }

    @Test
    void shouldReturnAuthoritiesSavingReport(){
        given(authorityDataSourceRepository.saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST_AUTHORITY_DETAILS_SET))
                .willReturn(MOCKED_PERSISTED_AUTHORITIES_SET);

        var report =authorityRequestProcessingService.saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST);

        assertThat(report.getPersistedAuthoritiesDetailsMap())
                .containsExactlyInAnyOrderEntriesOf(EXPECTED_PERSISTED_AUTHORITIES_MAP);
    }

    @Test
    void shouldDeleteInOrderAuthorityRoleBoundsAuthoritiesAndUpdateBoundedRolesTimestamps(){
        var authorityDelOrder = Mockito.inOrder(
                authorityDataSourceRepository,
                roleBindingDataSourceRepository,
                roleDataSourceRepository
        );
        given(roleBindingDataSourceRepository.getRoleIdsOfBoundedAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(MOCKED_BOUNDED_ROLES_IDS);
        given(roleBindingDataSourceRepository.deleteRoleBoundsOfAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(MOCKED_BOUNDED_ROLES_IDS.size());
        given(authorityDataSourceRepository.deleteAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS.size());
        given(roleDataSourceRepository.updateRoleTimestamp(eq(MOCKED_BOUNDED_ROLES_IDS), anyLong()))
                .willReturn(MOCKED_BOUNDED_ROLES_IDS.size());

        authorityRequestProcessingService.deleteAuthorities(TEST_AUTHORITY_DELETING_REQUEST);

        authorityDelOrder.verify(roleBindingDataSourceRepository).getRoleIdsOfBoundedAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS);
        authorityDelOrder.verify(roleBindingDataSourceRepository).deleteRoleBoundsOfAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS);
        authorityDelOrder.verify(authorityDataSourceRepository).deleteAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS);
        authorityDelOrder.verify(roleDataSourceRepository).updateRoleTimestamp(eq(MOCKED_BOUNDED_ROLES_IDS), anyLong());
    }

    @Test
    void shouldReturnAuthorityDelReport(){
        given(roleBindingDataSourceRepository.getRoleIdsOfBoundedAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(MOCKED_BOUNDED_ROLES_IDS);
        given(roleBindingDataSourceRepository.deleteRoleBoundsOfAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(MOCKED_BOUNDED_ROLES_IDS.size());
        given(authorityDataSourceRepository.deleteAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS.size());
        given(roleDataSourceRepository.updateRoleTimestamp(eq(MOCKED_BOUNDED_ROLES_IDS), anyLong()))
                .willReturn(MOCKED_BOUNDED_ROLES_IDS.size());

        var report = authorityRequestProcessingService.deleteAuthorities(TEST_AUTHORITY_DELETING_REQUEST);

        assertThat(report.getDeletedAuthoritiesQty()).isEqualTo(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS.size());
        assertThat(report.getDeletedRoleBounds()).isEqualTo(MOCKED_BOUNDED_ROLES_IDS.size());
        assertThat(report.getRoleIdWithUpdatedTimestamps()).containsExactlyInAnyOrderElementsOf(MOCKED_BOUNDED_ROLES_IDS);
    }

    @Test
    void shouldThrowDifferentAffectedRowsThanRequiredIfNumberOfUpdatedRolesIsDifferentThanBoundsClaimsQty(){
        given(roleBindingDataSourceRepository.getRoleIdsOfBoundedAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(MOCKED_BOUNDED_ROLES_IDS);
        given(roleBindingDataSourceRepository.deleteRoleBoundsOfAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(MOCKED_BOUNDED_ROLES_IDS.size());
        given(authorityDataSourceRepository.deleteAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS.size());
        given(roleDataSourceRepository.updateRoleTimestamp(eq(MOCKED_BOUNDED_ROLES_IDS), anyLong()))
                .willReturn(0);

        assertThatExceptionOfType(DifferentAffectedRowsThanRequired.class).isThrownBy(()->{
            authorityRequestProcessingService.deleteAuthorities(TEST_AUTHORITY_DELETING_REQUEST);
        });
    }

    @Test
    void shouldThrowDifferentAffectedRowsThanRequiredIfNumberOfDeletedBoundsIsDifferentThanBoundsQty(){
        given(roleBindingDataSourceRepository.getRoleIdsOfBoundedAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(MOCKED_BOUNDED_ROLES_IDS);
        given(roleBindingDataSourceRepository.deleteRoleBoundsOfAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(0);
        given(authorityDataSourceRepository.deleteAuthorities(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS))
                .willReturn(TEST_AUTHORITY_DEL_REQ_AUTHORITIES_IDS.size());

        assertThatExceptionOfType(DifferentAffectedRowsThanRequired.class).isThrownBy(()->{
            authorityRequestProcessingService.deleteAuthorities(TEST_AUTHORITY_DELETING_REQUEST);
        });
    }
}
