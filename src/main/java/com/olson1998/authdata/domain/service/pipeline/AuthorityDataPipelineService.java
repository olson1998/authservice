package com.olson1998.authdata.domain.service.pipeline;

import com.olson1998.authdata.domain.port.pipeline.AuthorityDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.pipeline.PipelineFactory;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthorityDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthorityDeletingRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthoritySavingRequest;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class AuthorityDataPipelineService implements AuthorityDatabaseOperationsPipeline {

    private final PipelineFactory pipelineFactory;

    private final AuthorityRequestProcessor authorityRequestProcessor;

    @Override
    public CompletableFuture<AuthoritySavingReport> runAuthoritySavingPipeline(AuthoritySavingRequest request) {
        return pipelineFactory.fabricate(request)
                .thenApply(authorityRequestProcessor::saveAuthorities);
    }

    @Override
    public CompletableFuture<AuthorityDeletingReport> runAuthorityDeletingPipeline(AuthorityDeletingRequest request) {
        return pipelineFactory.fabricate(request)
                .thenApply(authorityRequestProcessor::deleteAuthorities);
    }
}
