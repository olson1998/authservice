package com.olson1998.authservice.domain.port.data.repository;

import com.olson1998.authservice.domain.model.auth.data.UserDetails;
import com.olson1998.authservice.domain.port.data.entity.User;

import java.util.Optional;

public interface UserRepository {

    /**
     * Returns Optional user with given username
     * @param username username of user
     * @return Optional user retrieved from database
     */
    Optional<User> getUser(String username);

    /**
     * Persist new user from user details
     * @param userDetails user details containg values such username, password, password digest
     * @return saved user entity
     */
    User saveUser(UserDetails userDetails);
}
