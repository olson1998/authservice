package com.olson1998.authdata.domain.port.pipeline.stereotype;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public interface Pipeline<T> {

    <S> Pipeline<S> job(Function<T, S> pipeJob);

    CompletableFuture<T> end();
}
