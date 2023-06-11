package com.olson1998.authdata.domain.port.pipeline;

import com.olson1998.authdata.domain.port.processing.request.stereotype.Request;

import java.util.concurrent.CompletableFuture;

public interface PipelineFactory {

    <R extends Request> CompletableFuture<R> fabricate(R request);

    CompletableFuture<Void> fabricate();

    <O> O dematerializeContext(O object);
}
