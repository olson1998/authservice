package com.olson1998.authservice.domain.service.processing;

import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authservice.domain.port.processing.repository.RoleRequestProcessor;
import com.olson1998.authservice.domain.port.request.stereotype.RoleSavingRequest;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class RoleRequestProcessingService implements RoleRequestProcessor {

    private final RoleDataSourceRepository roleDataSourceRepository;

    @Override
    public Collection<Role> saveNewRoles(RoleSavingRequest request) {
        return null;
    }
}
