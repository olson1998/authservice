package com.olson1998.authservice.domain.port.processing.request.repository;

import com.olson1998.authservice.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authservice.domain.port.data.stereotype.Role;
import com.olson1998.authservice.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authservice.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authservice.domain.port.processing.request.stereotype.RoleBindingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.RoleSavingRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface RoleRequestProcessor {

    @Transactional(rollbackFor = RollbackRequiredException.class)
    RoleSavingReport saveNewRoles(RoleSavingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class)
    RoleBindingReport saveNewRoleBounds(RoleBindingRequest request);

}
