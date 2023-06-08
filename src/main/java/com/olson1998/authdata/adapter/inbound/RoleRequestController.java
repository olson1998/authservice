package com.olson1998.authdata.adapter.inbound;

import com.olson1998.authdata.application.requesting.model.RoleBindingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.RoleDeletingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.RoleSavingAdapterRequest;
import com.olson1998.authdata.domain.port.processing.pipeline.RoleDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleSavingReport;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Async
@RestController

@RequestMapping(value = "/role/data")
public class RoleRequestController {

    private final RoleDatabaseOperationsPipeline roleDatabaseOperationsPipeline;

    @PostMapping(path = "/save")
    public CompletableFuture<RoleSavingReport> interceptRoleSavingRequest(@RequestBody RoleSavingAdapterRequest request){
        return roleDatabaseOperationsPipeline.runRoleSavingRequestPipeline(request);
    }

    @DeleteMapping(path = "/del")
    public CompletableFuture<RoleDeletingReport> interceptRoleDeletingRequest(@RequestBody RoleDeletingAdapterRequest request){
        return roleDatabaseOperationsPipeline.runRoleDeletingPipeline(request);
    }

    @PostMapping(path = "/save/binding")
    public CompletableFuture<RoleBindingReport> interceptRoleBindingRequest(@RequestBody RoleBindingAdapterRequest request){
        return roleDatabaseOperationsPipeline.runRoleBindingPipeline(request);
    }
}