package com.olson1998.authdata.adapter.inbound;

import com.olson1998.authdata.application.requesting.model.AuthUserObtainAdapterRequest;
import com.olson1998.authdata.domain.port.pipeline.AuthDataPipeline;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Async
@RestController
@RequestMapping(value = "/auth/data")
public class AuthDataController {

    private final AuthDataPipeline authDataPipeline;

    @GetMapping(value = "/user")
    public CompletableFuture<AuthUser> getAuthUser(){
        return authDataPipeline.runGetAuthUserPipeline(new AuthUserObtainAdapterRequest());
    }
}
