package com.olson1998.authservice.application.data.repository;

import com.olson1998.authservice.application.data.entity.UserEntity;
import com.olson1998.authservice.domain.model.auth.data.UserDetails;
import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Service class working as proxy between application layer and domain layer
 */
@Service
@RequiredArgsConstructor
public class UserJpaRepositoryWrapper implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> getUser(@NonNull String username) {
        return userJpaRepository.getUserByUsername(username)
                .map(this::mapUser);
    }

    @Override
    @Transactional
    public User saveUser(UserDetails userDetails) {
        var userEntity = new UserEntity(userDetails);
        return userJpaRepository.save(userEntity);
    }

    private User mapUser(UserEntity userEntity){
        return Objects.requireNonNullElseGet(userEntity, null);
    }
}
