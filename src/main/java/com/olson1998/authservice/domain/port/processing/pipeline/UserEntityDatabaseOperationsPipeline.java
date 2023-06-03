package com.olson1998.authservice.domain.port.processing.pipeline;

import com.olson1998.authservice.domain.port.processing.report.stereotype.UserDeletingReport;
import com.olson1998.authservice.domain.port.processing.report.stereotype.UserSavingReport;
import com.olson1998.authservice.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authservice.domain.port.processing.request.stereotype.UserSavingRequest;

import java.util.concurrent.CompletableFuture;

public interface UserEntityDatabaseOperationsPipeline {

    CompletableFuture<UserSavingReport> runSaveUserPipeline(UserSavingRequest request);

    CompletableFuture<UserDeletingReport> runUserDeletePipeline(UserDeletingRequest request);
}
