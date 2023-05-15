package com.olson1998.authservice.domain.service.processing;

import com.olson1998.authservice.domain.port.data.entity.Role;
import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authservice.domain.port.processing.repository.RoleRequestProcessor;
import com.olson1998.authservice.domain.port.request.stereotype.RoleSavingRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
public class RoleRequestProcessingService implements RoleRequestProcessor {

    private final RoleDataSourceRepository roleDataSourceRepository;

    @Override
    public Collection<Role> saveNewRoles(@NonNull RoleSavingRequest request) {
        var persistedData = roleDataSourceRepository.saveRoles(request.getDetails());
        log.debug("saved new {} roles", persistedData.size());
        return persistedData;
    }

}
