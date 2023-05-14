package com.olson1998.authservice.domain.port.request.repository;

import com.olson1998.authservice.domain.port.request.entity.UserDetails;

public interface UserRepository {

    void save(UserDetails userDetails);

    boolean verifyPassword(String username, String password);

    boolean updatePassword(String username, String password);

}
