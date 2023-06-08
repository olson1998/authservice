package com.olson1998.authdata.domain.service.processing.pipeline;

import com.olson1998.authdata.domain.port.processing.pipeline.AuthorityDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.pipeline.PipelineFactory;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
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
}