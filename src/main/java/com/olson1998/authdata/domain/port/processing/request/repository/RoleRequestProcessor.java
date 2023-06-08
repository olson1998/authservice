package com.olson1998.authdata.domain.port.processing.request.repository;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBindingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRequestProcessor {

    @Transactional(rollbackFor = RollbackRequiredException.class)
    RoleSavingReport saveNewRoles(RoleSavingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class)
    RoleBindingReport saveNewRoleBounds(RoleBindingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class)
    RoleDeletingReport deleteRoles(RoleDeletingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class)
    int deleteUserRoles(UserDeletingRequest request);
}
