package com.olson1998.authservice.domain.service.processing;

import com.olson1998.authservice.application.datasource.entity.UserData;
import com.olson1998.authservice.application.datasource.entity.utils.SecretDigest;
import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authservice.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authservice.domain.port.data.repository.UserMembershipDataSourceRepository;
import com.olson1998.authservice.domain.port.request.data.UserDetails;
import com.olson1998.authservice.domain.port.request.data.UserMembershipClaim;
import com.olson1998.authservice.domain.port.request.stereotype.UserDeletingRequest;
import com.olson1998.authservice.domain.port.request.stereotype.UserSavingRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserRequestProcessingServiceTest {

    private static final long TEST_ID = 1L;

    private static final String TEST_USERNAME = "test";

    private static final SecretDigest TEST_DIGEST = SecretDigest.NONE;

    private static final String TEST_PASSWORD = "test";

    private static final User TEST_USER = new UserData(
            TEST_ID,
            TEST_USERNAME,
            TEST_PASSWORD,
            TEST_DIGEST
    );

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
    void shouldSaveUserUsingGivenUserDetails(){
        given(userSavingRequest.getUserDetails())
                .willReturn(userDetails);
        given(userDetails.getUsername())
                .willReturn(TEST_USERNAME);
        given(userSavingRequest.getMembershipClaims())
                .willReturn(userMembershipClaims());
        given(userDataSourceRepository.saveUser(userDetails))
                .willReturn(TEST_USER);

        userRequestProcessingService().saveUser(userSavingRequest);

        then(userDataSourceRepository).should().saveUser(userDetails);
    }

    @Test
    void shouldUpdateEachUserClaimWithGeneratedUserId(){
        given(userSavingRequest.getUserDetails())
                .willReturn(userDetails);
        given(userDetails.getUsername())
                .willReturn(TEST_USERNAME);
        given(userSavingRequest.getMembershipClaims())
                .willReturn(userMembershipClaims());
        given(userDataSourceRepository.saveUser(userDetails))
                .willReturn(TEST_USER);

        userRequestProcessingService().saveUser(userSavingRequest);

        then(userMembershipClaim).should().setUserId(TEST_ID);
    }

    @Test
    void shouldBindUserClaims(){
        var claims = userMembershipClaims();
        given(userSavingRequest.getUserDetails())
                .willReturn(userDetails);
        given(userDetails.getUsername())
                .willReturn(TEST_USERNAME);
        given(userSavingRequest.getMembershipClaims())
                .willReturn(claims);
        given(userDataSourceRepository.saveUser(userDetails))
                .willReturn(TEST_USER);

        userRequestProcessingService().saveUser(userSavingRequest);

        then(userMembershipDataSourceRepository).should().saveUserMemberships(claims);
    }

    @Test
    void shouldReturnUserAfterSaving(){
        given(userSavingRequest.getUserDetails())
                .willReturn(userDetails);
        given(userDetails.getUsername())
                .willReturn(TEST_USERNAME);
        given(userSavingRequest.getMembershipClaims())
                .willReturn(userMembershipClaims());
        given(userDataSourceRepository.saveUser(userDetails))
                .willReturn(TEST_USER);

        var user = userRequestProcessingService().saveUser(userSavingRequest);

        assertThat(user).isEqualTo(TEST_USER);
    }

    @Test
    void shouldDeleteUserWithGivenId() throws RollbackRequiredException {
        given(userDeletingRequest.getUserId()).willReturn(TEST_ID);
        given(userDataSourceRepository.deleteUser(TEST_ID)).willReturn(1);

        userRequestProcessingService().deleteUser(userDeletingRequest);

        then(userDataSourceRepository).should().deleteUser(TEST_ID);
    }

    @Test
    void shouldDeleteAllUserMemberships() throws RollbackRequiredException {
        given(userDeletingRequest.getUserId()).willReturn(TEST_ID);
        given(userDataSourceRepository.deleteUser(TEST_ID)).willReturn(1);

        userRequestProcessingService().deleteUser(userDeletingRequest);

        then(userMembershipDataSourceRepository).should().deleteUserMembership(TEST_ID);
    }

    @Test
    void shouldDeleteAllUserPrivateRolesByUserId() throws RollbackRequiredException {
        given(userDeletingRequest.getUserId()).willReturn(TEST_ID);
        given(userDataSourceRepository.deleteUser(TEST_ID)).willReturn(1);

        userRequestProcessingService().deleteUser(userDeletingRequest);

        then(roleDataSourceRepository).should().deleteAllPrivateRolesByUserId(TEST_ID);
    }

    @Test
    void shouldThrowNoBindingsEntityRowsDeletedExceptionIfNoUserHasBeenDeleted(){
        given(userDeletingRequest.getUserId()).willReturn(TEST_ID);
        given(userDataSourceRepository.deleteUser(TEST_ID)).willReturn(0);

        assertThatExceptionOfType(RollbackRequiredException.class)
                .isThrownBy(()-> userRequestProcessingService().deleteUser(userDeletingRequest));
    }

    @Test
    void shouldReturnRoleDeletingResultWithNumberOfDeletedPrivateRolesAndMemberships() throws RollbackRequiredException {
        int testDeletedRolesQty = 7;
        int testDeletedMemberships = 16;
        given(userDeletingRequest.getUserId())
                .willReturn(TEST_ID);
        given(userDataSourceRepository.deleteUser(TEST_ID))
                .willReturn(1);
        given(userMembershipDataSourceRepository.deleteUserMembership(TEST_ID))
                .willReturn(testDeletedMemberships);
        given(roleDataSourceRepository.deleteAllPrivateRolesByUserId(TEST_ID))
                .willReturn(testDeletedRolesQty);

        var report = userRequestProcessingService().deleteUser(userDeletingRequest);

        assertThat(report.getUserId()).isEqualTo(TEST_ID);
        assertThat(report.getDeletedPrivateRolesQty()).isEqualTo(testDeletedRolesQty);
        assertThat(report.getDeletedMembershipsBindingsQty()).isEqualTo(testDeletedMemberships);
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
