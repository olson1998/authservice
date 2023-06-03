package com.olson1998.authservice.domain.service.processing.request;

import com.olson1998.authservice.domain.port.data.stereotype.Authority;
import com.olson1998.authservice.domain.port.data.stereotype.Role;
import com.olson1998.authservice.domain.port.data.repository.RoleDataSourceRepository;
import com.olson1998.authservice.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authservice.domain.port.processing.request.stereotype.RoleSavingRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.olson1998.authservice.domain.service.processing.request.ProcessingRequestLogger.RequestType.SAVE;

@Slf4j
@RequiredArgsConstructor
public class RoleRequestProcessingService implements RoleRequestProcessor {

    private final RoleDataSourceRepository roleDataSourceRepository;

    @Override
    public List<Role> saveNewRoles(@NonNull RoleSavingRequest request) {
        ProcessingRequestLogger.log(log, request, SAVE, Role.class);
        return roleDataSourceRepository.saveRoles(request.getDetails());
    }

    @Override
    public int deleteUserPrivateRoles(long userId) {
        roleDataSourceRepository.deleteAllPrivateRolesByUserId(userId);
        return 0;
    }

}
