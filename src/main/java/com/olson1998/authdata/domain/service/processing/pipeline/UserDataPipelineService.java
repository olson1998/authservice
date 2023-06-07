package com.olson1998.authdata.domain.service.processing.pipeline;

import com.olson1998.authdata.domain.port.processing.pipeline.UserDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipBindReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserSavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipBindRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserSavingRequest;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class UserDataPipelineService implements UserDatabaseOperationsPipeline {

    private final UserRequestProcessor userRequestProcessor;

    @Override
    public CompletableFuture<UserSavingReport> runSaveUserPipeline(UserSavingRequest request) {
        return CompletableFuture.supplyAsync(()-> userRequestProcessor.saveUser(request));
    }

    @Override
    public CompletableFuture<UserDeletingReport> runUserDeletePipeline(UserDeletingRequest request) {
        return CompletableFuture.supplyAsync(()-> userRequestProcessor.deleteUser(request));
    }

    @Override
    public CompletableFuture<UserMembershipBindReport> runUserMembershipBindPipeline(UserMembershipBindRequest request) {
        return null;
    }
}
