package com.olson1998.authdata.domain.service.processing;

import com.olson1998.authdata.domain.model.processing.request.LinkedAuthoritySavingRequest;
import com.olson1998.authdata.domain.port.data.repository.AuthorityDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.RoleBindingDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleDetails;
import com.olson1998.authdata.domain.service.processing.request.RoleRequestProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.olson1998.authdata.application.datasource.entity.AuthorityTestDataSet.*;
import static com.olson1998.authdata.application.datasource.entity.RoleBindingTestDataSet.*;
import static com.olson1998.authdata.application.datasource.entity.RoleTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.RoleRequestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.AuthorityDetailsTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.RoleDetailsTestDataSet.*;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RoleRequestProcessingServiceTest {

    private static final List<Role> MOCKED_PERSISTED_ROLES = List.of(
            TEST_ROLE_DATA_1,
            TEST_ROLE_DATA_2,
            TEST_ROLE_DATA_3,
            TEST_ROLE_DATA_4,
            TEST_ROLE_DATA_5
    );

    private static final List<RoleBinding> MOCKED_PERSISTED_ROLE_BINDINGS = List.of(
            TEST_ROLE_BINDING_1,
            TEST_ROLE_BINDING_2,
            TEST_ROLE_BINDING_3,
            TEST_ROLE_BINDING_4,
            TEST_ROLE_BINDING_5,
            TEST_ROLE_BINDING_6
    );

    private static final Set<String> MOCKED_PERSISTED_ROLES_IDS = MOCKED_PERSISTED_ROLE_BINDINGS.stream()
            .map(RoleBinding::getRoleId)
            .collect(Collectors.toUnmodifiableSet());

    private static final Map<String, RoleDetails> EXPECTED_SAVED_ROLE_ID_ROLE_DET_MAP = Map.ofEntries(
            entry(TEST_ROLE_1_ID, TEST_ROLE_DETAILS_FORM_1),
            entry(TEST_ROLE_2_ID, TEST_ROLE_DETAILS_FORM_2),
            entry(TEST_ROLE_3_ID, TEST_ROLE_DETAILS_FORM_3),
            entry(TEST_ROLE_4_ID, TEST_ROLE_DETAILS_FORM_4),
            entry(TEST_ROLE_5_ID, TEST_ROLE_DETAILS_FORM_5)
    );

    private static final Map<String, Set<String>> EXPECTED_SAVED_ROLE_BOUNDS = Map.ofEntries(
            entry(TEST_ROLE_1_ID, Set.of(TEST_AUTHORITY_1_ID, TEST_AUTHORITY_2_ID)),
            entry(TEST_ROLE_2_ID, Set.of(TEST_AUTHORITY_1_ID, TEST_AUTHORITY_2_ID)),
            entry(TEST_ROLE_3_ID, Set.of(TEST_AUTHORITY_1_ID, TEST_AUTHORITY_2_ID))
    );

    @Mock
    private AuthorityRequestProcessor authorityRequestProcessor;

    @Mock
    private RoleDataSourceRepository roleDataSourceRepository;

    @Mock
    private RoleBindingDataSourceRepository roleBindingDataSourceRepository;

    @InjectMocks
    private RoleRequestProcessingService roleRequestProcessingService;

    @Test
    void shouldSaveRoles(){
        roleRequestProcessingService.saveNewRoles(TEST_ROLE_SAVING_REQUEST);

        then(roleDataSourceRepository).should().saveRoles(TEST_ROLE_SAVING_REQUEST_DETAILS);
    }

    @Test
    void shouldReturnRoleSavingReport(){
        given(roleDataSourceRepository.saveRoles(TEST_ROLE_SAVING_REQUEST_DETAILS))
                .willReturn(MOCKED_PERSISTED_ROLES);

        var report = roleRequestProcessingService.saveNewRoles(TEST_ROLE_SAVING_REQUEST);

        assertThat(report.getPersistedRolesDetailsMap())
                .containsExactlyInAnyOrderEntriesOf(EXPECTED_SAVED_ROLE_ID_ROLE_DET_MAP);
    }

    @Test
    void shouldSaveInOrderRoleBindingsAndThenUpdateBoundedRolesTimestamps(){
        var roleBoundsSavingOrder = Mockito.inOrder(
                roleBindingDataSourceRepository,
                roleDataSourceRepository
        );
        given(roleBindingDataSourceRepository.saveRoleBindings(TEST_ROLE_BOUNDS_REQUEST_CLAIMS))
                .willReturn(MOCKED_PERSISTED_ROLE_BINDINGS);
        given(roleDataSourceRepository.updateRoleTimestamp(eq(MOCKED_PERSISTED_ROLES_IDS), anyLong()))
                .willReturn(MOCKED_PERSISTED_ROLES_IDS.size());

        roleRequestProcessingService.saveNewRoleBounds(TEST_ROLE_BINDING_REQUEST);

        roleBoundsSavingOrder.verify(roleBindingDataSourceRepository).saveRoleBindings(TEST_ROLE_BOUNDS_REQUEST_CLAIMS);
        roleBoundsSavingOrder.verify(roleDataSourceRepository).updateRoleTimestamp(eq(MOCKED_PERSISTED_ROLES_IDS), anyLong());
    }

    @Test
    void shouldReturnRoleBoundsSavingReport(){
        given(roleBindingDataSourceRepository.saveRoleBindings(TEST_ROLE_BOUNDS_REQUEST_CLAIMS))
                .willReturn(MOCKED_PERSISTED_ROLE_BINDINGS);
        given(roleDataSourceRepository.updateRoleTimestamp(eq(MOCKED_PERSISTED_ROLES_IDS), anyLong()))
                .willReturn(MOCKED_PERSISTED_ROLES_IDS.size());

        var report =roleRequestProcessingService.saveNewRoleBounds(TEST_ROLE_BINDING_REQUEST);

        assertThat(report.getSavedRoleBindings())
                .containsExactlyInAnyOrderEntriesOf(EXPECTED_SAVED_ROLE_BOUNDS);
    }
}
