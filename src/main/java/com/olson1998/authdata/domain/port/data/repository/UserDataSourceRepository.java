package com.olson1998.authdata.domain.port.data.repository;

import com.olson1998.authdata.domain.port.data.stereotype.Role;
import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;
import com.olson1998.authdata.domain.port.security.stereotype.AuthUser;

import java.util.Optional;
import java.util.Set;

public interface UserDataSourceRepository {

    Optional<AuthUser> getAuthUserByUsername(String username);

    Set<Role> getUserRoles(long userId);

    Set<ExtendedAuthorityTimestamp> getAuthorityTimestamps(long userId);

    User saveUser(UserDetails userDetails);

    int deleteUser(long userId);
}
