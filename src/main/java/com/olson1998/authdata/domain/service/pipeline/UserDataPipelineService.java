package com.olson1998.authdata.domain.service.pipeline;

import com.olson1998.authdata.domain.port.pipeline.repository.PipelineFactory;
import com.olson1998.authdata.domain.port.pipeline.repository.UserDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipBindReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserSavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipSavingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserSavingRequest;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class UserDataPipelineService implements UserDatabaseOperationsPipeline {

    private final PipelineFactory pipelineFactory;

    private final UserRequestProcessor userRequestProcessor;

    @Override
    public CompletableFuture<UserSavingReport> runSaveUserPipeline(UserSavingRequest request) {
        return pipelineFactory.fabricate(request)
                .job(userRequestProcessor::saveUser)
                .end();
    }

    @Override
    public CompletableFuture<UserDeletingReport> runUserDeletePipeline(UserDeletingRequest request) {
        return pipelineFactory.fabricate(request)
                .job(userRequestProcessor::deleteUser)
                .end();
    }

    @Override
    public CompletableFuture<UserMembershipBindReport> runUserMembershipBindPipeline(UserMembershipSavingRequest request) {
        return pipelineFactory.fabricate(request)
                .job(userRequestProcessor::bindMemberships)
                .end();
    }

    @Override
    public CompletableFuture<UserMembershipDeletingReport> runUserMembershipDeletingPipeline(UserMembershipDeletingRequest request) {
        return pipelineFactory.fabricate(request)
                .job(userRequestProcessor::deleteMemberships)
                .end();
    }
}
