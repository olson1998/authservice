package com.olson1998.authdata.domain.service.processing.pipeline;

import com.olson1998.authdata.domain.port.processing.pipeline.PipelineFactory;
import com.olson1998.authdata.domain.port.processing.request.stereotype.Request;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@NoArgsConstructor
public class DomainPipelineFactory implements PipelineFactory {
    
    @Override
    public <R extends Request> CompletableFuture<R> fabricate(R request) {
        log.debug("fabricating pipeline for request '{}' of class: {}", request.getId(), request.getClass().getSimpleName());
        return CompletableFuture.supplyAsync(()-> this.createRequestPipeline(request));
    }
    
    private <R> R createRequestPipeline(R request){
        return request;
    }
}
