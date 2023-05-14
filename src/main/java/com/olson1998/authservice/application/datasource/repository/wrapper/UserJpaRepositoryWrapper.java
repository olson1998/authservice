package com.olson1998.authservice.application.datasource.repository.wrapper;

import com.olson1998.authservice.application.datasource.entity.RoleData;
import com.olson1998.authservice.application.datasource.entity.UserData;
import com.olson1998.authservice.application.datasource.entity.utils.PasswordDigest;
import com.olson1998.authservice.application.datasource.repository.jpa.UserJpaRepository;
import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.data.repository.UserRepository;
import com.olson1998.authservice.domain.port.data.utils.PasswordEncryption;
import com.olson1998.authservice.domain.port.request.entity.UserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
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
    public Set<Role> getUserRoles(@NonNull String username) {
        return userJpaRepository.selectUserRoles(username).stream()
                .map(UserJpaRepositoryWrapper::mapRole)
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
        return Objects.requireNonNullElseGet(userData, null);
    }

    protected static Role mapRole(RoleData roleData){
        return Objects.requireNonNullElse(roleData, null);
    }

    private static PasswordEncryption mapPasswordDigest(PasswordDigest digest){
        return Objects.requireNonNullElse(digest, null);
    }
}
