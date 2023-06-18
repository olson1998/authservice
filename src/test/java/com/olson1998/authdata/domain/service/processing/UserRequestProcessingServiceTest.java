package com.olson1998.authdata.domain.service.processing;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authdata.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authdata.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import com.olson1998.authdata.domain.service.processing.request.UserRequestProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.*;
import static com.olson1998.authdata.application.requesting.model.UserRequestDataSet.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserRequestProcessingServiceTest {

    private static final UserDetails TEST_USER_SAVE_REQUEST_DETAILS =
            TEST_USER_SAVING_REQUEST.getUserDetails();

    private static final Set<UserMembershipClaim> TEST_USER_MEMBERSHIP_CLAIMS =
            TEST_USER_SAVING_REQUEST_WITH_MEMBERSHIP_CLAIM.getMembershipClaims();

    @Mock
    private RoleRequestProcessor roleRequestProcessor;

    @Mock
    private UserDataSourceRepository userDataSourceRepository;

    @Mock
    private UserMembershipDataSourceRepository userMembershipDataSourceRepository;

    @Test
    void shouldSaveUserUsingGivenUserDetails() throws RollbackRequiredException {
        given(userDataSourceRepository.saveUser(TEST_USER_SAVE_REQUEST_DETAILS))
                .willReturn(TEST_USER_DATA);

        userRequestProcessingService().saveUser(TEST_USER_SAVING_REQUEST);

        then(userDataSourceRepository).should().saveUser(TEST_USER_SAVE_REQUEST_DETAILS);
    }

    @Test
    void shouldSaveUserMembershipClaims(){
        given(userDataSourceRepository.saveUser(any()))
                .willReturn(TEST_USER_DATA);

        userRequestProcessingService().saveUser(TEST_USER_SAVING_REQUEST_WITH_MEMBERSHIP_CLAIM);

        then(userMembershipDataSourceRepository).should().saveUserMemberships(TEST_USER_DATA.getId(), TEST_USER_MEMBERSHIP_CLAIMS);
    }

    @Test
    void shouldIncludePersistedDataIntoReport(){
        given(userDataSourceRepository.saveUser(any()))
                .willReturn(TEST_USER_DATA);

        var report =
                userRequestProcessingService().saveUser(TEST_USER_SAVING_REQUEST_WITH_MEMBERSHIP_CLAIM);

        assertThat(report.getUserId()).isEqualTo(TEST_USER_ID);
        assertThat(report.getUsername()).isEqualTo(TEST_USER_USERNAME);
        assertThat(report.getRequestId()).isEqualTo(TEST_USER_SAVING_REQUEST_ID );
    }

    @Test
    void shouldDeleteUserMembershipsAllPrivateRolesForAndUserDetailsGivenUserId(){
        given(userDataSourceRepository.deleteUser(TEST_USER_ID))
                .willReturn(1);

        userRequestProcessingService().deleteUser(TEST_USER_DELETING_REQUEST);

        then(userMembershipDataSourceRepository).should().deleteAllUserMemberships(TEST_USER_ID);
        then(roleRequestProcessor).should().deleteUserRoles(TEST_USER_DELETING_REQUEST);
        then(userDataSourceRepository).should().deleteUser(TEST_USER_ID);
    }

    @Test
    void shouldThrowRollbackRequiredExceptionIfRepositoryReturnedZeroDeletedUsers(){
        given(userDataSourceRepository.deleteUser(TEST_USER_ID))
                .willReturn(0);

        assertThatExceptionOfType(RollbackRequiredException.class).isThrownBy(()->{
            userRequestProcessingService().deleteUser(TEST_USER_DELETING_REQUEST);
        });
    }

    @Test
    void shouldSaveUserMemberships(){
        userRequestProcessingService().bindMemberships(TEST_USER_MEMBERSHIP_SAVING_REQUEST);

        then(userMembershipDataSourceRepository).should().saveUserMemberships(
                TEST_USER_ID,
                TEST_USER_MEMBERSHIP_SAVING_REQUEST.getUserMembershipClaims()
        );
    }
    private UserRequestProcessingService userRequestProcessingService(){
        return new UserRequestProcessingService(
                roleRequestProcessor,
                userDataSourceRepository,
                userMembershipDataSourceRepository
        );
    }
}
