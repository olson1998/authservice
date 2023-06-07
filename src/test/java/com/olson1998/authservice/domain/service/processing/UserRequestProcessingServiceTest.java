package com.olson1998.authservice.domain.service.processing;

import com.olson1998.authservice.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authservice.domain.port.data.repository.*;
import com.olson1998.authservice.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.UserDetails;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import com.olson1998.authservice.domain.service.processing.request.AuthorityRequestProcessingService;
import com.olson1998.authservice.domain.service.processing.request.RoleRequestProcessingService;
import com.olson1998.authservice.domain.service.processing.request.UserRequestProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.olson1998.authservice.application.datasource.entity.UserTestDataSet.*;
import static com.olson1998.authservice.application.requesting.model.UserRequestDataSet.*;
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
    private UserDataSourceRepository userDataSourceRepository;

    @Mock
    private UserMembershipDataSourceRepository userMembershipDataSourceRepository;

    @Mock
    private RoleDataSourceRepository roleDataSourceRepository;

    @Mock
    private RoleBindingDataSourceRepository roleBindingDataSourceRepository;

    @Mock
    private AuthorityDataSourceRepository authorityDataSourceRepository;

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

        then(userMembershipDataSourceRepository).should().saveUserMemberships(TEST_USER_MEMBERSHIP_CLAIMS);
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

        then(userMembershipDataSourceRepository).should().deleteUserMembership(TEST_USER_ID);
        then(roleDataSourceRepository).should().deleteAllPrivateRolesByUserId(TEST_USER_ID);
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

    private AuthorityRequestProcessingService authorityRequestProcessingService(){
        return new AuthorityRequestProcessingService(authorityDataSourceRepository);
    }

    private RoleRequestProcessingService roleRequestProcessingService(){
        return new RoleRequestProcessingService(
                authorityRequestProcessingService(),
                roleDataSourceRepository,
                roleBindingDataSourceRepository
        );
    }

    private UserRequestProcessingService userRequestProcessingService(){
        return new UserRequestProcessingService(
                roleRequestProcessingService(),
                userDataSourceRepository,
                userMembershipDataSourceRepository
        );
    }
}
