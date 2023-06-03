package com.olson1998.authservice.domain.port.processing.request.repository;

import com.olson1998.authservice.domain.port.data.stereotype.Role;
import com.olson1998.authservice.domain.port.processing.request.stereotype.RoleSavingRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleRequestProcessor {

    @Transactional
    List<Role> saveNewRoles(RoleSavingRequest request);

    int deleteUserPrivateRoles(long userId);
}
