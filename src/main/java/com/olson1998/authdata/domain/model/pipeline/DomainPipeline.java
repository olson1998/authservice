package com.olson1998.authdata.domain.model.pipeline;

import com.olson1998.authdata.domain.port.pipeline.stereotype.Pipeline;
import com.olson1998.authdata.domain.port.processing.datasource.TenantThreadDataSource;
import com.olson1998.authdata.domain.port.processing.request.repository.RequestContextHolder;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
public class DomainPipeline<T> implements Pipeline<T> {

    private final CompletableFuture<T> pipeline;

    private final AtomicReference<RequestContext> requestContextRef;

    private final RequestContextHolder requestContextHolder;

    private final TenantThreadDataSource tenantThreadDataSource;

    @Override
    public <S> Pipeline<S> job(@NonNull Function<T, S> pipeJob) {
        return new DomainPipeline<>(
                pipeline.thenApplyAsync(this::inheritRequestContext)
                        .thenApplyAsync(pipeJob)
                        .thenApplyAsync(this::cleanJobContext),
                requestContextRef,
                requestContextHolder,
                tenantThreadDataSource
        );
    }

    @Override
    public CompletableFuture<T> end() {
        return pipeline.exceptionally(this::handleException);
    }

    private T inheritRequestContext(T value){
        log.trace("Running next job on thread: '{}'", Thread.currentThread().getId());
        requestContextHolder.setCurrentContext(requestContextRef.get());
        tenantThreadDataSource.setCurrentForTenant(requestContextRef.get().getTenantId());
        return value;
    }

    private <S> S cleanJobContext(S value){
        log.trace("Cleaning thread: '{}'", Thread.currentThread().getId());
        requestContextHolder.clean();
        tenantThreadDataSource.clean();
        return value;
    }

    private T handleException(Throwable e){
        this.cleanJobContext(null);
        throw new CompletionException(e);
    }
}
