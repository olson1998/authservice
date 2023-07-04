package com.olson1998.authdata.domain.service.pipeline;

import com.olson1998.authdata.domain.model.pipeline.DomainPipeline;
import com.olson1998.authdata.domain.port.pipeline.repository.PipelineFactory;
import com.olson1998.authdata.domain.port.pipeline.stereotype.Pipeline;
import com.olson1998.authdata.domain.port.processing.datasource.TenantThreadDataSource;
import com.olson1998.authdata.domain.port.processing.request.repository.RequestContextHolder;
import com.olson1998.authdata.domain.port.processing.request.stereotype.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j

@Service
@RequiredArgsConstructor
public class PipelineService implements PipelineFactory {

    private final RequestContextHolder requestContextHolder;

    private final TenantThreadDataSource tenantThreadDataSource;

    @Override
    public Pipeline<Void> fabricate() {
        var requestContextRef = new AtomicReference<>(requestContextHolder.getRequestContext());
        requestContextHolder.clean();
        tenantThreadDataSource.clean();
        return new DomainPipeline<>(
                CompletableFuture.runAsync(this::logFabricatingPipe),
                requestContextRef,
                requestContextHolder,
                tenantThreadDataSource
        );
    }

    @Override
    public <R extends Request> Pipeline<R> fabricate(R request) {
        var requestContextRef = new AtomicReference<>(requestContextHolder.getRequestContext());
        requestContextHolder.clean();
        tenantThreadDataSource.clean();
        return new DomainPipeline<>(
                CompletableFuture.supplyAsync(()-> request),
                requestContextRef,
                requestContextHolder,
                tenantThreadDataSource
        );
    }

    private void logFabricatingPipe(){
        log.debug("Fabricating pipeline");
    }
}
