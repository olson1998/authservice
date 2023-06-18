package com.olson1998.authdata.domain.service.processing;

import com.olson1998.authdata.domain.port.data.repository.AuthorityDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Authority;
import com.olson1998.authdata.domain.port.data.stereotype.RoleBinding;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBoundsDeletingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import com.olson1998.authdata.domain.service.processing.request.AuthorityRequestProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.olson1998.authdata.application.datasource.entity.AuthorityTestDataSet.*;
import static com.olson1998.authdata.application.datasource.entity.RoleBindingTestDataSet.*;
import static com.olson1998.authdata.application.datasource.entity.RoleTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.AuthorityRequestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.AuthorityDetailsTestDataSet.*;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AuthorityRequestProcessingServiceTest {

    private static final List<Authority> MOCKED_PERSISTED_AUTHORITIES_SET = List.of(
            TEST_AUTHORITY_DATA_1,
            TEST_AUTHORITY_DATA_2,
            TEST_AUTHORITY_DATA_3
    );

    private static final Set<RoleBinding> MOCKED_ROLE_BINDING_PROVIDING_FUN_RESPONSE = Set.of(
            TEST_ROLE_BINDING_1,
            TEST_ROLE_BINDING_2,
            TEST_ROLE_BINDING_3,
            TEST_ROLE_BINDING_4,
            TEST_ROLE_BINDING_5
    );

    private static final Set<String> MOCKED_AUTHORITIES_BINDING_ROLES_IDS = MOCKED_ROLE_BINDING_PROVIDING_FUN_RESPONSE.stream()
            .map(RoleBinding::getRoleId)
            .collect(Collectors.toUnmodifiableSet());

    private static final Map<String, Integer> MOCKED_ROLE_BOUNDS_DELETING_QTY_MAP = Map.ofEntries(
            entry(TEST_ROLE_1_ID, 2),
            entry(TEST_ROLE_2_ID, 2),
            entry(TEST_ROLE_3_ID, 2)
    );

    private static final Map<String, AuthorityDetails> EXPECTED_PERSISTED_AUTHORITIES_MAP = Map.ofEntries(
            entry(TEST_AUTHORITY_1_ID, TEST_AUTHORITY_DETAILS_FORM_1),
            entry(TEST_AUTHORITY_2_ID, TEST_AUTHORITY_DETAILS_FORM_2),
            entry(TEST_AUTHORITY_3_ID, TEST_AUTHORITY_DETAILS_FORM_3)
    );

    @Mock
    private AuthorityDataSourceRepository authorityDataSourceRepository;

    @Mock
    private Function<RoleBoundDeletingRequest, RoleBoundsDeletingReport> roleBoundDeletingRequestFunction;

    @Mock
    private Function<Set<String>, Set<RoleBinding>> roleBindingsByAuthoritiesIdsProvidingFunction;

    @Mock
    private BiFunction<Set<String>, Long, Integer> roleTimestampUpdatingFunction;

    @Mock
    private RoleBoundsDeletingReport roleBoundsDeletingReport;

    @Captor
    private ArgumentCaptor<Long> timestampCaptor;

    @Test
    void shouldSaveAuthorities(){
        authorityRequestProcessingService().saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST);

        then(authorityDataSourceRepository).should().saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST_AUTHORITY_DETAILS_SET);
    }

    @Test
    void shouldReturnAuthoritiesSavingReportWithPersistedAuthoritiesMap(){
        given(authorityDataSourceRepository.saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST_AUTHORITY_DETAILS_SET))
                .willReturn(MOCKED_PERSISTED_AUTHORITIES_SET);

        var report = authorityRequestProcessingService().saveAuthorities(TEST_AUTHORITY_SAVING_REQUEST);

        assertThat(report.getRequestId()).isEqualTo(TEST_AUTHORITY_SAVING_REQUEST_ID);
        assertThat(report.getPersistedAuthoritiesDetailsMap())
                .containsExactlyInAnyOrderEntriesOf(EXPECTED_PERSISTED_AUTHORITIES_MAP);
    }

    @Test
    void shouldDeleteAuthorities(){
        given(roleBindingsByAuthoritiesIdsProvidingFunction.apply(TEST_AUTHORITY_DELETING_REQUEST_AUTHORITIES_IDS))
                .willReturn(MOCKED_ROLE_BINDING_PROVIDING_FUN_RESPONSE);
        given(roleBoundDeletingRequestFunction.apply(any(RoleBoundDeletingRequest.class)))
                .willReturn(roleBoundsDeletingReport);
        given(roleTimestampUpdatingFunction.apply(eq(MOCKED_AUTHORITIES_BINDING_ROLES_IDS), anyLong()))
                .willReturn(MOCKED_AUTHORITIES_BINDING_ROLES_IDS.size());
        given(authorityDataSourceRepository.deleteAuthorities(TEST_AUTHORITY_DELETING_REQUEST_AUTHORITIES_IDS))
                .willReturn(TEST_AUTHORITY_DELETING_REQUEST_AUTHORITIES_IDS.size());
        given(roleBoundsDeletingReport.getDeletedRolesBoundsQty())
                .willReturn(MOCKED_ROLE_BOUNDS_DELETING_QTY_MAP);

        authorityRequestProcessingService().deleteAuthorities(TEST_AUTHORITY_DELETING_REQUEST);

        then(authorityDataSourceRepository).should().deleteAuthorities(TEST_AUTHORITY_DELETING_REQUEST_AUTHORITIES_IDS);
    }

    @Test
    void shouldUpdateRoleTimestampWithCurrentTimeInMils(){
        var testStartedEpoch = System.currentTimeMillis();

        given(roleBindingsByAuthoritiesIdsProvidingFunction.apply(TEST_AUTHORITY_DELETING_REQUEST_AUTHORITIES_IDS))
                .willReturn(MOCKED_ROLE_BINDING_PROVIDING_FUN_RESPONSE);
        given(roleBoundDeletingRequestFunction.apply(any(RoleBoundDeletingRequest.class)))
                .willReturn(roleBoundsDeletingReport);
        given(roleTimestampUpdatingFunction.apply(eq(MOCKED_AUTHORITIES_BINDING_ROLES_IDS), timestampCaptor.capture()))
                .willReturn(MOCKED_AUTHORITIES_BINDING_ROLES_IDS.size());
        given(authorityDataSourceRepository.deleteAuthorities(TEST_AUTHORITY_DELETING_REQUEST_AUTHORITIES_IDS))
                .willReturn(TEST_AUTHORITY_DELETING_REQUEST_AUTHORITIES_IDS.size());
        given(roleBoundsDeletingReport.getDeletedRolesBoundsQty())
                .willReturn(MOCKED_ROLE_BOUNDS_DELETING_QTY_MAP);

        authorityRequestProcessingService().deleteAuthorities(TEST_AUTHORITY_DELETING_REQUEST);

        var testEndedEpoch = System.currentTimeMillis();

        assertThat(timestampCaptor.getValue()).isBetween(testStartedEpoch, testEndedEpoch);
    }

    private AuthorityRequestProcessingService authorityRequestProcessingService(){
        return new AuthorityRequestProcessingService(
                authorityDataSourceRepository,
                roleBoundDeletingRequestFunction,
                roleBindingsByAuthoritiesIdsProvidingFunction,
                roleTimestampUpdatingFunction
        );
    }
}
