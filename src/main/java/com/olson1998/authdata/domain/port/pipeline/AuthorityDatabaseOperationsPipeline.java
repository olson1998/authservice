package com.olson1998.authdata.domain.port.pipeline;

import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthorityDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthorityDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthoritySavingRequest;

import java.util.concurrent.CompletableFuture;

public interface AuthorityDatabaseOperationsPipeline {

    CompletableFuture<AuthoritySavingReport> runAuthoritySavingPipeline(AuthoritySavingRequest request);

    CompletableFuture<AuthorityDeletingReport> runAuthorityDeletingPipeline(AuthorityDeletingRequest request);
}
