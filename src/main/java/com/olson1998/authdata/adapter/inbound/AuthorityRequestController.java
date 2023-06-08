package com.olson1998.authdata.adapter.inbound;

import com.olson1998.authdata.application.requesting.model.AuthorityDeletingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.AuthoritySavingAdapterRequest;
import com.olson1998.authdata.domain.port.processing.pipeline.AuthorityDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthorityDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.AuthoritySavingReport;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Async
@RestController
@RequestMapping(value = "/authority/data")
public class AuthorityRequestController {

    private final AuthorityDatabaseOperationsPipeline authorityDatabaseOperationsPipeline;

    @PostMapping(value = "/save")
    public CompletableFuture<AuthoritySavingReport> interceptAuthoritySaveRequest(@RequestBody AuthoritySavingAdapterRequest request){
        return authorityDatabaseOperationsPipeline.runAuthoritySavingPipeline(request);
    }

    @DeleteMapping(value = "/del")
    public CompletableFuture<AuthorityDeletingReport> interceptAuthorityDeletingRequest(@RequestBody AuthorityDeletingAdapterRequest request){
        return authorityDatabaseOperationsPipeline.runAuthorityDeletingPipeline(request);
    }
}
