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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
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
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RoleRequestProcessingServiceTest {

    private static final Set<RoleDetails> TEST_ROLE_DETAILS =
            TEST_ROLE_SAVING_REQUEST.getDetails();

    private static final Set<RoleBindingClaim> TEST_ROLE_BINDING_CLAIMS =
            TEST_ROLE_BINDING_REQUEST.getRolesBindingsClaims();

    private static final Set<AuthorityDetails> EXPECTED_LINKED_AUTHORITIES_TO_SAVE =
            new LinkedAuthoritySavingRequest(TEST_ROLE_BINDING_REQUEST_WITH_SAVE_CLAIM)
            .getAuthoritiesDetails();

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

    private static final Map<String, AuthorityDetails> MOCKED_SAVED_AUTHORITIES_MAP = Map.ofEntries(
            entry(TEST_AUTHORITY_1_ID, TEST_AUTHORITY_DETAILS_FORM_1),
            entry(TEST_AUTHORITY_2_ID, TEST_AUTHORITY_DETAILS_FORM_2),
            entry(TEST_AUTHORITY_3_ID, TEST_AUTHORITY_DETAILS_FORM_3)
    );

    @Mock
    private AuthorityRequestProcessor authorityRequestProcessor;

    @Mock
    private RoleDataSourceRepository roleDataSourceRepository;

    @Mock
    private RoleBindingDataSourceRepository roleBindingDataSourceRepository;

    @Mock
    private AuthorityDataSourceRepository authorityDataSourceRepository;

    @Mock
    private AuthoritySavingReport authoritySavingReport;

    @Captor
    private ArgumentCaptor<AuthoritySavingRequest> authoritySavingRequestCaptor;

    @Test
    void shouldSaveGivenRoleDetails(){
        roleRequestProcessingService().saveNewRoles(TEST_ROLE_SAVING_REQUEST);

        then(roleDataSourceRepository).should().saveRoles(TEST_ROLE_DETAILS);
    }

    @Test
    void shouldReturnReportBasedOnRequestDetails(){
        given(roleDataSourceRepository.saveRoles(anySet()))
                .willReturn(MOCKED_PERSISTED_ROLES);

        var report =
                roleRequestProcessingService().saveNewRoles(TEST_ROLE_SAVING_REQUEST);

        assertThat(report.getRequestId()).isEqualTo(TEST_ROLE_SAVING_REQUEST_ID);
        assertThat(report.getPersistedRolesDetailsMap()).containsOnlyKeys(
                List.of(TEST_ROLE_1_ID, TEST_ROLE_2_ID, TEST_ROLE_3_ID ,TEST_ROLE_4_ID, TEST_ROLE_5_ID)
        );
    }

    @Test
    void shouldSaveGivenRoleBindings(){
        roleRequestProcessingService().saveNewRoleBounds(TEST_ROLE_BINDING_REQUEST);

        then(roleBindingDataSourceRepository).should().saveRoleBindings(TEST_ROLE_BINDING_CLAIMS);
    }

    @Test
    void shouldNotSaveAnyAuthoritiesIfRequestAuthoritySaveRequestClaimIsEmpty(){
        assertThat(TEST_ROLE_BINDING_REQUEST.getRoleIdAuthoritySavingRequestMap()).isEmpty();

        roleRequestProcessingService().saveNewRoleBounds(TEST_ROLE_BINDING_REQUEST);

        then(authorityDataSourceRepository).shouldHaveNoInteractions();
    }

    @Test
    void shouldReturnSetOfBoundedRolesIdsAndAuthoritiesId(){
        given(roleBindingDataSourceRepository.saveRoleBindings(anySet()))
                .willReturn(MOCKED_PERSISTED_ROLE_BINDINGS);
        given(roleDataSourceRepository.updateRoleTimestamp(anySet(), anyLong()))
                .willReturn(getMockedPersistedRoleBindingsRolesQty());

        var report =
                roleRequestProcessingService().saveNewRoleBounds(TEST_ROLE_BINDING_REQUEST);

        var savedRoleBindings = report.getSavedRoleBindings();
        assertThat(savedRoleBindings).containsOnlyKeys(List.of(TEST_ROLE_1_ID, TEST_ROLE_2_ID, TEST_ROLE_3_ID));
        assertThat(savedRoleBindings.get(TEST_ROLE_1_ID)).containsExactlyInAnyOrder(TEST_AUTHORITY_1_ID, TEST_AUTHORITY_2_ID);
        assertThat(savedRoleBindings.get(TEST_ROLE_2_ID)).containsExactlyInAnyOrder(TEST_AUTHORITY_1_ID, TEST_AUTHORITY_2_ID);
        assertThat(savedRoleBindings.get(TEST_ROLE_3_ID)).containsExactlyInAnyOrder(TEST_AUTHORITY_1_ID, TEST_AUTHORITY_2_ID);
    }

    @Test
    void shouldSaveLinkedAuthoritiesIfAnyPresent(){
        given(authorityRequestProcessor.saveAuthorities(any()))
                .willReturn(authoritySavingReport);
        given(authoritySavingReport.getPersistedAuthoritiesDetailsMap())
                .willReturn(MOCKED_SAVED_AUTHORITIES_MAP);

        roleRequestProcessingService().saveNewRoleBounds(TEST_ROLE_BINDING_REQUEST_WITH_SAVE_CLAIM);

        then(authorityRequestProcessor).should().saveAuthorities(authoritySavingRequestCaptor.capture());

        var authoritySavingReq = authoritySavingRequestCaptor.getValue();

        assertThat(authoritySavingReq.getAuthoritiesDetails()).containsAll(EXPECTED_LINKED_AUTHORITIES_TO_SAVE);
    }

    private RoleRequestProcessingService roleRequestProcessingService(){
        return new RoleRequestProcessingService(
                authorityRequestProcessor,
                roleDataSourceRepository,
                roleBindingDataSourceRepository
        );
    }


    private int getMockedPersistedRoleBindingsRolesQty(){
        return MOCKED_PERSISTED_ROLE_BINDINGS.stream()
                .map(RoleBinding::getRoleId)
                .collect(Collectors.toSet())
                .size();
    }

}
