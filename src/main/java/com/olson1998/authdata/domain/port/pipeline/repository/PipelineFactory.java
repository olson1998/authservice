package com.olson1998.authdata.domain.port.pipeline.repository;

import com.olson1998.authdata.domain.port.pipeline.stereotype.Pipeline;
import com.olson1998.authdata.domain.port.processing.request.stereotype.Request;

public interface PipelineFactory {

    Pipeline<Void> fabricate();

    <R extends Request> Pipeline<R> fabricate(R request);
}
