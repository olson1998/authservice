package com.olson1998.authservice.domain.service.processing;

import com.olson1998.authservice.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authservice.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authservice.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authservice.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.UserSavingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.UserDetails;
import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.UserMembershipClaim;
import com.olson1998.authservice.domain.service.processing.request.UserRequestProcessingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.olson1998.authservice.application.datasource.entity.UserTestDataSet.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserRequestProcessingServiceTest {

    @Mock
    private UserMembershipClaim userMembershipClaim;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UserSavingRequest userSavingRequest;

    @Mock
    private UserDeletingRequest userDeletingRequest;

    @Mock
    private UserDataSourceRepository userDataSourceRepository;

    @Mock
    private UserMembershipDataSourceRepository userMembershipDataSourceRepository;

    @Mock
    private RoleDataSourceRepository roleDataSourceRepository;

    @Test
    void shouldSaveUserUsingGivenUserDetails() throws RollbackRequiredException {
        given(userSavingRequest.getUserDetails())
                .willReturn(userDetails);
        given(userDetails.getUsername())
                .willReturn(TEST_USER_USERNAME);
        given(userDetails.getPassword())
                .willReturn(TEST_USER_PASSWORD);
        given(userSavingRequest.getMembershipClaims())
                .willReturn(userMembershipClaims());
        given(userDataSourceRepository.saveUser(userDetails))
                .willReturn(TEST_USER_DATA);

        userRequestProcessingService().saveUser(userSavingRequest);

        then(userDataSourceRepository).should().saveUser(userDetails);
    }

    @Test
    void shouldUpdateEachUserClaimWithGeneratedUserId() throws RollbackRequiredException {
        given(userSavingRequest.getUserDetails())
                .willReturn(userDetails);
        given(userDetails.getUsername())
                .willReturn(TEST_USER_USERNAME);
        given(userDetails.getPassword())
                .willReturn(TEST_USER_PASSWORD);
        given(userSavingRequest.getMembershipClaims())
                .willReturn(userMembershipClaims());
        given(userDataSourceRepository.saveUser(userDetails))
                .willReturn(TEST_USER_DATA);

        userRequestProcessingService().saveUser(userSavingRequest);

        then(userMembershipClaim).should().setUserId(TEST_USER_ID);
    }

    @Test
    void shouldBindUserClaims() throws RollbackRequiredException {
        var claims = userMembershipClaims();
        given(userSavingRequest.getUserDetails())
                .willReturn(userDetails);
        given(userDetails.getUsername())
                .willReturn(TEST_USER_USERNAME);
        given(userDetails.getPassword())
                .willReturn(TEST_USER_PASSWORD);
        given(userSavingRequest.getMembershipClaims())
                .willReturn(claims);
        given(userDataSourceRepository.saveUser(userDetails))
                .willReturn(TEST_USER_DATA);

        userRequestProcessingService().saveUser(userSavingRequest);

        then(userMembershipDataSourceRepository).should().saveUserMemberships(claims);
    }

    @Test
    void shouldReturnUserAfterSaving() throws RollbackRequiredException {
        given(userSavingRequest.getUserDetails())
                .willReturn(userDetails);
        given(userDetails.getUsername())
                .willReturn(TEST_USER_USERNAME);
        given(userDetails.getPassword())
                .willReturn(TEST_USER_PASSWORD);
        given(userDataSourceRepository.saveUser(userDetails))
                .willReturn(TEST_USER_DATA);

        var user = userRequestProcessingService().saveUser(userSavingRequest);

        assertThat(user).isEqualTo(TEST_USER_DATA);
    }

    @Test
    void shouldDeleteUserWithGivenId() throws RollbackRequiredException {
        given(userDeletingRequest.getUserId()).willReturn(TEST_USER_ID);
        given(userDataSourceRepository.deleteUser(TEST_USER_ID)).willReturn(1);

        userRequestProcessingService().deleteUser(userDeletingRequest);

        then(userDataSourceRepository).should().deleteUser(TEST_USER_ID);
    }

    @Test
    void shouldDeleteAllUserMemberships() throws RollbackRequiredException {
        given(userDeletingRequest.getUserId()).willReturn(TEST_USER_ID);
        given(userDataSourceRepository.deleteUser(TEST_USER_ID)).willReturn(1);

        userRequestProcessingService().deleteUser(userDeletingRequest);

        then(userMembershipDataSourceRepository).should().deleteUserMembership(TEST_USER_ID);
    }

    @Test
    void shouldDeleteAllUserPrivateRolesByUserId() throws RollbackRequiredException {
        given(userDeletingRequest.getUserId()).willReturn(TEST_USER_ID);
        given(userDataSourceRepository.deleteUser(TEST_USER_ID)).willReturn(1);

        userRequestProcessingService().deleteUser(userDeletingRequest);

        then(roleDataSourceRepository).should().deleteAllPrivateRolesByUserId(TEST_USER_ID);
    }

    @Test
    void shouldThrowNoBindingsEntityRowsDeletedExceptionIfNoUserHasBeenDeleted(){
        given(userDeletingRequest.getUserId()).willReturn(TEST_USER_ID);
        given(userDataSourceRepository.deleteUser(TEST_USER_ID)).willReturn(0);

        assertThatExceptionOfType(RollbackRequiredException.class)
                .isThrownBy(()-> userRequestProcessingService().deleteUser(userDeletingRequest));
    }

    private Set<UserMembershipClaim> userMembershipClaims(){
        return Set.of(userMembershipClaim);
    }

    private UserRequestProcessingService userRequestProcessingService(){
        return new UserRequestProcessingService(
                userDataSourceRepository,
                userMembershipDataSourceRepository,
                roleDataSourceRepository
        );
    }
}
