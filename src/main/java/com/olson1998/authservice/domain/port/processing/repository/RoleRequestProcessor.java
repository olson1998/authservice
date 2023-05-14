package com.olson1998.authservice.domain.port.processing.repository;

import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.request.model.RoleSavingRequest;

import java.util.Collection;

public interface RoleRequestProcessor {

    Collection<Role> saveNewRoles(RoleSavingRequest request);
}
