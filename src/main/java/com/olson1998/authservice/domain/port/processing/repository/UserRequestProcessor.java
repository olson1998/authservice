package com.olson1998.authservice.domain.port.processing.repository;

import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.request.model.UserSavingRequest;
import org.springframework.transaction.annotation.Transactional;

public interface UserRequestProcessor {

    /**
     * Save new user and all bindings
     * @param request Request containing all required data to perform persisting
     * @return save user
     */
    @Transactional
    User saveUser(UserSavingRequest request);
}