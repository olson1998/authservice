package com.olson1998.authdata.domain.port.processing.pipeline;

import com.olson1998.authdata.domain.port.processing.request.stereotype.Request;

import java.util.concurrent.CompletableFuture;

public interface PipelineFactory {

    <R extends Request> CompletableFuture<R> fabricate(R request);
}
