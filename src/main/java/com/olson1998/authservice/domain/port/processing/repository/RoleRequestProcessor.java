package com.olson1998.authservice.domain.port.processing.repository;

import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.request.stereotype.RoleSavingRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface RoleRequestProcessor {

    @Transactional
    Collection<Role> saveNewRoles(RoleSavingRequest request);
}
