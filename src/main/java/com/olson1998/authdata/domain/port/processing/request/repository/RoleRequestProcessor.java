package com.olson1998.authdata.domain.port.processing.request.repository;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBoundsDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

public interface RoleRequestProcessor {

    @Transactional(rollbackFor = RollbackRequiredException.class, transactionManager = "tenantDatasourceTransactionManager")
    RoleSavingReport saveNewRoles(RoleSavingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class, transactionManager = "tenantDatasourceTransactionManager")
    RoleBindingReport saveNewRoleBounds(RoleBoundSavingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class, transactionManager = "tenantDatasourceTransactionManager")
    RoleDeletingReport deleteRoles(RoleDeletingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class, transactionManager = "tenantDatasourceTransactionManager")
    RoleBoundsDeletingReport deleteRoleBounds(RoleBoundDeletingRequest request);

    @Transactional(rollbackFor = RollbackRequiredException.class, transactionManager = "tenantDatasourceTransactionManager")
    int deleteUserRoles(UserDeletingRequest request);
}
