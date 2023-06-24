package com.olson1998.authdata.application.datasource.repository.tenant.wrapper;

import com.olson1998.authdata.application.datasource.entity.tenant.UserData;
import com.olson1998.authdata.application.datasource.entity.tenant.values.ExtendedAuthorityTimestampData;
import com.olson1998.authdata.application.datasource.entity.tenant.values.SecretDigest;
import com.olson1998.authdata.application.datasource.repository.tenant.spring.UserJpaRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authdata.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authdata.domain.port.data.utils.SecretEncryption;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class working as proxy between application layer and domain layer
 */
@Service
@RequiredArgsConstructor
public class UserJpaRepositoryWrapper implements UserDataSourceRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> getUser(long userId) {
        return userJpaRepository.selectUserById(userId)
                .map(User.class::cast);
    }

    @Override
    public Set<Role> getUserRoles(long userId) {
        return userJpaRepository.selectUserRoles(userId).stream()
                .map(RoleJpaRepositoryWrapper::mapRole)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ExtendedAuthorityTimestamp> getAuthorityTimestamps(long userId) {
        return userJpaRepository.selectUserAuthoritiesTimestamps(userId).stream()
                .map(UserJpaRepositoryWrapper::mapAuthorityTimestamp)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<SecretEncryption> getUserPasswordDigest(@NonNull String username) {
        return userJpaRepository.selectUserPasswordDigest(username)
                .map(UserJpaRepositoryWrapper::mapPasswordDigest);
    }

    @Override
    public User saveUser(UserDetails userDetails) {
        var userData = new UserData(userDetails);
        return userJpaRepository.save(userData);
    }

    @Override
    public int deleteUser(long userId) {
        return userJpaRepository.deleteUserById(userId);
    }

    private static User mapUser(UserData userData){
        return userData;
    }

    private static ExtendedAuthorityTimestamp mapAuthorityTimestamp(ExtendedAuthorityTimestampData data){
        return data;
    }

    private static SecretEncryption mapPasswordDigest(SecretDigest digest){
        return digest;
    }
}
