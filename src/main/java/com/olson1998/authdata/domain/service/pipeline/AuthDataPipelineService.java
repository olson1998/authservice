package com.olson1998.authdata.domain.service.pipeline;

import com.olson1998.authdata.domain.port.pipeline.AuthDataPipeline;
import com.olson1998.authdata.domain.port.pipeline.PipelineFactory;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthUserRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthUserObtainRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthUser;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class AuthDataPipelineService implements AuthDataPipeline {

    private final PipelineFactory pipelineFactory;

    private final AuthUserRequestProcessor authUserRequestProcessor;

    @Override
    public CompletableFuture<AuthUser> runGetAuthUserPipeline(AuthUserObtainRequest authUserObtainRequest) {
        return pipelineFactory.fabricate(authUserObtainRequest)
                .thenApplyAsync(empty -> authUserRequestProcessor.getAuthUser(authUserObtainRequest))
                .thenApplyAsync(pipelineFactory::dematerializeContext);
    }
}
