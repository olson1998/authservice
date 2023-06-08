package com.olson1998.authdata.domain.port.processing.pipeline;

import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBindingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleSavingRequest;

import java.util.concurrent.CompletableFuture;

public interface RoleDatabaseOperationsPipeline {

    CompletableFuture<RoleSavingReport> runRoleSavingRequestPipeline(RoleSavingRequest request);

    CompletableFuture<RoleBindingReport> runRoleBindingPipeline(RoleBindingRequest request);

    CompletableFuture<RoleDeletingReport> runRoleDeletingPipeline(RoleDeletingRequest request);
}
