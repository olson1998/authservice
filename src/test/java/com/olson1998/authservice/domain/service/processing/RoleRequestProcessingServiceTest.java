package com.olson1998.authservice.domain.service.processing;

import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authservice.domain.port.data.stereotype.Role;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleDetails;
import com.olson1998.authservice.domain.service.processing.request.RoleRequestProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static com.olson1998.authservice.application.datasource.entity.RoleTestDataSet.*;
import static com.olson1998.authservice.application.requesting.model.RoleRequestDataSet.TEST_ROLE_SAVING_REQUEST;
import static com.olson1998.authservice.application.requesting.model.RoleRequestDataSet.TEST_ROLE_SAVING_REQUEST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class RoleRequestProcessingServiceTest {

    private static final Set<RoleDetails> TEST_ROLE_DETAILS = TEST_ROLE_SAVING_REQUEST.getDetails();

    private static final List<Role> MOCKED_PERSISTED_ROLES = List.of(
            TEST_ROLE_DATA_1,
            TEST_ROLE_DATA_2,
            TEST_ROLE_DATA_3,
            TEST_ROLE_DATA_4,
            TEST_ROLE_DATA_5
    );

    @Mock
    private RoleDataSourceRepository roleDataSourceRepository;

    @InjectMocks
    private RoleRequestProcessingService roleRequestProcessingService;

    @Test
    void shouldSaveGivenRoleDetails(){
        roleRequestProcessingService.saveNewRoles(TEST_ROLE_SAVING_REQUEST);

        then(roleDataSourceRepository).should().saveRoles(TEST_ROLE_DETAILS);
    }

    @Test
    void shouldReturnReportBasedOnRequestDetails(){
        given(roleDataSourceRepository.saveRoles(anySet()))
                .willReturn(MOCKED_PERSISTED_ROLES);

        var report =
                roleRequestProcessingService.saveNewRoles(TEST_ROLE_SAVING_REQUEST);

        assertThat(report.getRequestId()).isEqualTo(TEST_ROLE_SAVING_REQUEST_ID);
        assertThat(report.getPersistedRolesDetailsMap()).containsOnlyKeys(
                List.of(TEST_ROLE_1_ID, TEST_ROLE_2_ID, TEST_ROLE_3_ID ,TEST_ROLE_4_ID, TEST_ROLE_5_ID)
        );
    }
}
