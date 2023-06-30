package com.olson1998.authdata.domain.service.processing;

import com.olson1998.authdata.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.UserSecretDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.UserMembership;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import com.olson1998.authdata.domain.service.processing.request.UserRequestProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.olson1998.authdata.application.datasource.entity.UserMembershipTestDataSet.*;
import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.UserRequestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.payload.UserDetailsTestDataSet.TEST_USER_DETAILS;
import static com.olson1998.authdata.application.requesting.model.payload.UserMembershipClaimTestDataSet.*;
import static java.util.Map.entry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserRequestProcessingServiceTest {

    private static final List<UserMembership> MOCKED_SAVED_USER_MEMBERSHIPS = List.of(
            TEST_USER_COMPANY_MEMBERSHIP,
            TEST_USER_GROUP_MEMBERSHIP,
            TEST_USER_REGION_MEMBERSHIP,
            TEST_USER_TEAM_MEMBERSHIP
    );

    private static final Map<String, UserMembershipClaim> EXPECTED_USER_MEM_SAVING_REPORT_SAVED_ID_CLAIMS_MAP = Map.ofEntries(
            entry(TEST_USER_COMPANY_MEMBERSHIP_ID, TEST_USER_COMPANY_MEMBERSHIP_CLAIM),
            entry(TEST_USER_GROUP_MEMBERSHIP_ID, TEST_USER_GROUP_MEMBERSHIP_CLAIM),
            entry(TEST_USER_REGION_MEMBERSHIP_ID, TEST_USER_REGION_MEMBERSHIP_CLAIM),
            entry(TEST_USER_TEAM_MEMBERSHIP_ID, TEST_USER_TEAM_MEMBERSHIP_CLAIM)
    );

    @Mock
    private RoleRequestProcessor roleRequestProcessor;

    @Mock
    private UserDataSourceRepository userDataSourceRepository;

    @Mock
    private UserSecretDataSourceRepository userSecretDataSourceRepository;

    @Mock
    private UserMembershipDataSourceRepository userMembershipDataSourceRepository;

    @InjectMocks
    private UserRequestProcessingService userRequestProcessingService;

    @Test
    void shouldSaveInOrderUserDetailsThenUserSecretThenPrivateRole(){
        var userSavingOrder = Mockito.inOrder(
                roleRequestProcessor,
                userDataSourceRepository,
                userSecretDataSourceRepository,
                userMembershipDataSourceRepository
        );

        given(userDataSourceRepository.saveUser(TEST_USER_DETAILS))
                .willReturn(TEST_USER_DATA);

        userRequestProcessingService.saveUser(TEST_USER_SAVING_REQUEST);

        userSavingOrder.verify(userDataSourceRepository).saveUser(TEST_USER_DETAILS);
        userSavingOrder.verify(userSecretDataSourceRepository).saveUserSecret(TEST_USER_ID, TEST_USER_ENC_PASSWORD);
        userSavingOrder.verify(roleRequestProcessor).saveNewUserPrivateRole(TEST_USER_ID);
    }

    @Test
    void shouldSaveUserMembershipsIfAnyPresent(){
        given(userDataSourceRepository.saveUser(TEST_USER_DETAILS))
                .willReturn(TEST_USER_DATA);

        userRequestProcessingService.saveUser(TEST_USER_SAVING_REQUEST_WITH_MEMBERSHIP_CLAIM);

        then(userMembershipDataSourceRepository).should()
                .saveUserMemberships(TEST_USER_ID, TEST_MEMBERSHIP_CLAIMS_SET);
    }

    @Test
    void shouldReturnUserSavingReport(){
        given(userDataSourceRepository.saveUser(TEST_USER_DETAILS))
                .willReturn(TEST_USER_DATA);

        var report = userRequestProcessingService.saveUser(TEST_USER_SAVING_REQUEST);

        assertThat(report.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(report.getUsername()).isEqualTo(TEST_USER_USERNAME);
    }

    @Test
    void shouldDeleteInOrderUserMembershipsThenDeleteSecretThenPrivateRolesAndFinallyDeleteUser(){
        var userDeletingOrder = Mockito.inOrder(
                userMembershipDataSourceRepository,
                userSecretDataSourceRepository,
                roleRequestProcessor,
                userDataSourceRepository
        );

        given(userDataSourceRepository.deleteUser(TEST_USER_ID))
                .willReturn(1);

        userRequestProcessingService.deleteUser(TEST_USER_DELETING_REQUEST);

        userDeletingOrder.verify(userMembershipDataSourceRepository).deleteAllUserMemberships(TEST_USER_ID);
        userDeletingOrder.verify(userSecretDataSourceRepository).deleteUserSecret(TEST_USER_ID);
        userDeletingOrder.verify(roleRequestProcessor).deleteUserRoles(TEST_USER_DELETING_REQUEST);
        userDeletingOrder.verify(userDataSourceRepository).deleteUser(TEST_USER_ID);
    }

    @Test
    void shouldSaveUserMemberships(){
        userRequestProcessingService.bindMemberships(TEST_USER_MEMBERSHIP_SAVING_REQUEST);

        then(userMembershipDataSourceRepository).should().saveUserMemberships(TEST_USER_ID, TEST_MEMBERSHIP_CLAIMS_SET);
    }

    @Test
    void shouldReturnUserMembershipsSavingReport(){
        given(userMembershipDataSourceRepository.saveUserMemberships(TEST_USER_ID, TEST_MEMBERSHIP_CLAIMS_SET))
                .willReturn(MOCKED_SAVED_USER_MEMBERSHIPS);

        var report = userRequestProcessingService.bindMemberships(TEST_USER_MEMBERSHIP_SAVING_REQUEST);

        assertThat(report.getUserId())
                .isEqualTo(TEST_USER_ID);
        assertThat(report.getSavedMembershipsClaims())
                .containsExactlyInAnyOrderEntriesOf(EXPECTED_USER_MEM_SAVING_REPORT_SAVED_ID_CLAIMS_MAP);
    }

    @Test
    void shouldDeleteUserMemberships(){
        userRequestProcessingService.deleteMemberships(TEST_USER_MEMBERSHIPS_DELETING_REQUEST);

        then(userMembershipDataSourceRepository).should().deleteUserRegionMembership(TEST_USER_ID, TEST_USER_MEM_DEL_REQ_REGIONAL_MEM_IDS);
        then(userMembershipDataSourceRepository).should().deleteUserGroupMembership(TEST_USER_ID, TEST_USER_MEM_DEL_REQ_GROUP_MEM_IDS);
        then(userMembershipDataSourceRepository).should().deleteUserTeamMembership(TEST_USER_ID, TEST_USER_MEM_DEL_REQ_TEAM_MEM_IDS);
    }

    @Test
    void shouldReturnUserMemDelReport(){
        given(userMembershipDataSourceRepository.deleteUserRegionMembership(TEST_USER_ID, TEST_USER_MEM_DEL_REQ_REGIONAL_MEM_IDS))
                .willReturn(TEST_USER_MEM_DEL_REQ_REGIONAL_MEM_IDS.size());
        given(userMembershipDataSourceRepository.deleteUserGroupMembership(TEST_USER_ID, TEST_USER_MEM_DEL_REQ_GROUP_MEM_IDS))
                .willReturn(TEST_USER_MEM_DEL_REQ_GROUP_MEM_IDS.size());
        given(userMembershipDataSourceRepository.deleteUserTeamMembership(TEST_USER_ID, TEST_USER_MEM_DEL_REQ_TEAM_MEM_IDS))
                .willReturn(TEST_USER_MEM_DEL_REQ_TEAM_MEM_IDS.size());

        var report = userRequestProcessingService.deleteMemberships(TEST_USER_MEMBERSHIPS_DELETING_REQUEST);

        assertThat(report.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(report.getDeletedGroupMemberships()).isOne();
        assertThat(report.getDeletedRegionMemberships()).isOne();
        assertThat(report.getDeletedTeamMemberships()).isOne();
    }

}
