package com.olson1998.authdata.domain.port.pipeline.repository;

import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBoundsDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleSavingRequest;

import java.util.concurrent.CompletableFuture;

public interface RoleDatabaseOperationsPipeline {

    CompletableFuture<RoleSavingReport> runRoleSavingRequestPipeline(RoleSavingRequest request);

    CompletableFuture<RoleBindingReport> runRoleBindingPipeline(RoleBoundSavingRequest request);

    CompletableFuture<RoleDeletingReport> runRoleDeletingPipeline(RoleDeletingRequest request);

    CompletableFuture<RoleBoundsDeletingReport> runRoleBoundsDeletingPipeline(RoleBoundDeletingRequest request);
}
