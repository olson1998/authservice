package com.olson1998.authdata.domain.port.pipeline;

import com.olson1998.authdata.domain.port.processing.request.stereotype.AuthUserObtainRequest;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthUser;

import java.util.concurrent.CompletableFuture;

public interface AuthDataPipeline {

    CompletableFuture<AuthUser> runGetAuthUserPipeline(AuthUserObtainRequest authUserObtainRequest);
}
