package com.olson1998.authdata.application.processing.pipeline;

import com.olson1998.authdata.application.processing.pipeline.exception.PipelineFabricationException;
import com.olson1998.authdata.domain.port.pipeline.PipelineFactory;
import com.olson1998.authdata.domain.port.processing.request.stereotype.Request;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j

@Service
@NoArgsConstructor
public class ApplicationPipelineFactory implements PipelineFactory {
    
    @Override
    public <R extends Request> CompletableFuture<R> fabricate(R request) {
        return CompletableFuture.supplyAsync(()-> this.createRequestPipeline(request));
    }
    
    private <R extends Request> R createRequestPipeline(R request){
        if(request != null){
            log.debug("fabricating pipeline for request '{}' of class: {}", request.getId(), request.getClass().getSimpleName());
            return request;
        }else {
            throw new PipelineFabricationException(log);
        }
    }
}
