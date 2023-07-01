package com.olson1998.authdata.domain.service.pipeline;

import com.olson1998.authdata.domain.port.pipeline.PipelineFactory;
import com.olson1998.authdata.domain.port.pipeline.RoleDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBoundsDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleSavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleBoundSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.RoleSavingRequest;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class RoleDataPipelineService implements RoleDatabaseOperationsPipeline {

    private final PipelineFactory pipelineFactory;

    private final RoleRequestProcessor roleRequestProcessor;

    @Override
    public CompletableFuture<RoleSavingReport> runRoleSavingRequestPipeline(RoleSavingRequest request) {
        return pipelineFactory.fabricate(request)
                .thenApplyAsync(roleRequestProcessor::saveNewRoles)
                .thenApplyAsync(pipelineFactory::dematerializeContext);
    }

    @Override
    public CompletableFuture<RoleBindingReport> runRoleBindingPipeline(RoleBoundSavingRequest request) {
        return pipelineFactory.fabricate(request)
                .thenApplyAsync(roleRequestProcessor::saveNewRoleBounds)
                .thenApplyAsync(pipelineFactory::dematerializeContext);
    }

    @Override
    public CompletableFuture<RoleDeletingReport> runRoleDeletingPipeline(RoleDeletingRequest request) {
        return pipelineFactory.fabricate(request)
                .thenApplyAsync(roleRequestProcessor::deleteRoles)
                .thenApplyAsync(pipelineFactory::dematerializeContext);
    }

    @Override
    public CompletableFuture<RoleBoundsDeletingReport> runRoleBoundsDeletingPipeline(RoleBoundDeletingRequest request) {
        return pipelineFactory.fabricate(request)
                .thenApplyAsync(roleRequestProcessor::deleteRoleBounds)
                .thenApplyAsync(pipelineFactory::dematerializeContext);
    }
}
