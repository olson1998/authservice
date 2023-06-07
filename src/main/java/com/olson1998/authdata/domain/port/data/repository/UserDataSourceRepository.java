package com.olson1998.authdata.domain.port.data.repository;

import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authdata.domain.port.data.utils.SecretEncryption;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;

import java.util.Optional;
import java.util.Set;

public interface UserDataSourceRepository {

    /**
     * Search SQL database for User entity by unique username
     * @param username User's username
     * @return User wrapped in Optional interface, if user not found returns Optional.empty()
     */
    Optional<User> getUser(String username);

    /**
     * Search SQL database for User roles
     * @param username User's username
     * @return Set of user roles, empty set if no roles find
     */
    Set<Role> getUserRoles(long userId);

    Set<ExtendedAuthorityTimestamp> getAuthorityTimestamps(long userId);

    /**
     * Search SQL database for User's password digest by unique username
     * @param username User's username
     * @return PasswordDigest wrapped in Optional interface, if digest not found returns Optional.empty()
     */
    Optional<SecretEncryption> getUserPasswordDigest(String username);

    /**
     * Insert data in Sql database from UserDetails form
     * @param userDetails form containing crucial data such username password and password digest
     */
    User saveUser(UserDetails userDetails);

    /**
     * Delete data related to User with given username
     * @param userId User's id
     * @return number of deleted rows
     */
    int deleteUser(long userId);
}
