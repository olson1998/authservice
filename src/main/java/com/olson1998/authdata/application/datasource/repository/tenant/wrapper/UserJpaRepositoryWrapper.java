package com.olson1998.authdata.application.datasource.repository.tenant.wrapper;

import com.olson1998.authdata.application.datasource.entity.tenant.UserData;
import com.olson1998.authdata.application.datasource.repository.tenant.spring.UserJpaRepository;
import com.olson1998.authdata.domain.port.data.repository.UserDataSourceRepository;
import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import com.olson1998.authdata.domain.port.security.stereotype.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public Optional<AuthUser> getAuthUserByUsername(String username) {
        return userJpaRepository.selectUserAuthDataByUsername(username)
                .map(AuthUser.class::cast);
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
                .map(ExtendedAuthorityTimestamp.class::cast)
                .collect(Collectors.toSet());
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

}
