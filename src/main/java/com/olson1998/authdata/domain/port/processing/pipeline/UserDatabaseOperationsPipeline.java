package com.olson1998.authdata.domain.port.processing.pipeline;

import com.olson1998.authdata.domain.port.processing.report.stereotype.UserDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipBindReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserSavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipBindRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserMembershipDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserSavingRequest;

import java.util.concurrent.CompletableFuture;

public interface UserDatabaseOperationsPipeline {

    CompletableFuture<UserSavingReport> runSaveUserPipeline(UserSavingRequest request);

    CompletableFuture<UserDeletingReport> runUserDeletePipeline(UserDeletingRequest request);

    CompletableFuture<UserMembershipBindReport> runUserMembershipBindPipeline(UserMembershipBindRequest request);

    CompletableFuture<UserMembershipDeletingReport> runUserMembershipDeletingPipeline(UserMembershipDeletingRequest request);
}
