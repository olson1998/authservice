package com.olson1998.authdata.application.pipeline;

import com.olson1998.authdata.application.pipeline.exception.PipelineFabricationException;
import com.olson1998.authdata.application.requesting.AdapterRequestContextHolder;
import com.olson1998.authdata.domain.port.pipeline.PipelineFactory;
import com.olson1998.authdata.domain.port.processing.request.stereotype.Request;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import static com.olson1998.authdata.application.requesting.AdapterRequestContextHolder.getLocalThreadRequestContext;

@Slf4j

@Service
@NoArgsConstructor
public class ApplicationPipelineFactory implements PipelineFactory {
    
    @Override
    public <R extends Request> CompletableFuture<R> fabricate(R request) {
        var requestContextRef = new AtomicReference<>(getLocalThreadRequestContext());
        AdapterRequestContextHolder.cleanContext();
        return CompletableFuture.supplyAsync(()-> this.createRequestPipeline(request))
                .thenApply(req -> inheritRequestContext(request, requestContextRef));
    }

    @Override
    public CompletableFuture<Void> fabricate() {
        var requestContextRef = new AtomicReference<>(getLocalThreadRequestContext());
        AdapterRequestContextHolder.cleanContext();
        return CompletableFuture.runAsync(()-> inheritRequestContext(requestContextRef));
    }

    @Override
    public <O> O dematerializeContext(O object) {
        AdapterRequestContextHolder.cleanContext();
        return object;
    }

    private <R extends Request> R inheritRequestContext(R request, AtomicReference<RequestContext> requestContextRef){
        AdapterRequestContextHolder.setLocalThreadRequestContext(requestContextRef.get());
        return request;
    }

    private void inheritRequestContext(AtomicReference<RequestContext> requestContextRef){
        AdapterRequestContextHolder.setLocalThreadRequestContext(requestContextRef.get());
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
