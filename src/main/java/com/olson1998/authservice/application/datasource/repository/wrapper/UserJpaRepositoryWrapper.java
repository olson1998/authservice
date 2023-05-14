package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.UserData;
import com.olson1998.authservice.application.datasource.entity.utils.ExtendedAuthorityTimestampData;
import com.olson1998.authservice.application.datasource.entity.utils.SecretDigest;
import com.olson1998.authservice.application.datasource.repository.jpa.UserJpaRepository;
import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.data.repository.UserRepository;
import com.olson1998.authservice.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authservice.domain.port.data.utils.PasswordEncryption;
import com.olson1998.authservice.domain.port.request.entity.UserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class working as proxy between application layer and domain layer
 */
@Service
@RequiredArgsConstructor
public class UserJpaRepositoryWrapper implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> getUser(@NonNull String username) {
        return userJpaRepository.selectUserByUsername(username)
                .map(UserJpaRepositoryWrapper::mapUser);
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
    public Optional<PasswordEncryption> getUserPasswordDigest(@NonNull String username) {
        return userJpaRepository.selectUserPasswordDigest(username)
                .map(UserJpaRepositoryWrapper::mapPasswordDigest);
    }

    @Override
    @Transactional
    public void saveUser(UserDetails userDetails) {
        var userData = new UserData(userDetails);
        userJpaRepository.save(userData);
    }

    @Override
    @Transactional
    public int deleteUser(@NonNull String username) {
        return userJpaRepository.deleteUserByUsername(username);
    }

    private static User mapUser(UserData userData){
        return userData;
    }

    private static ExtendedAuthorityTimestamp mapAuthorityTimestamp(ExtendedAuthorityTimestampData data){
        return data;
    }

    private static PasswordEncryption mapPasswordDigest(SecretDigest digest){
        return digest;
    }
}
