package com.olson1998.authdata.adapter.inbound;

import com.olson1998.authdata.application.requesting.model.RoleBoundSavingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.RoleBoundsDeletingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.RoleDeletingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.RoleSavingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.payload.RoleBoundDeletingForm;
import com.olson1998.authdata.application.requesting.model.payload.RoleDetailsForm;
import com.olson1998.authdata.domain.port.pipeline.RoleDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBindingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleBoundsDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.RoleSavingReport;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Async
@RestController

@RequestMapping(value = "/role/data")
public class RoleRequestController {

    private final RoleDatabaseOperationsPipeline roleDatabaseOperationsPipeline;

    @PostMapping(path = "/save")
    public CompletableFuture<RoleSavingReport> interceptRoleSavingRequest(@RequestBody Set<RoleDetailsForm> roleDetailsForms){
        return roleDatabaseOperationsPipeline.runRoleSavingRequestPipeline(new RoleSavingAdapterRequest(roleDetailsForms));
    }

    @DeleteMapping(path = "/del")
    public CompletableFuture<RoleDeletingReport> interceptRoleDeletingRequest(@RequestBody Set<String> roleIdSet){
        return roleDatabaseOperationsPipeline.runRoleDeletingPipeline(new RoleDeletingAdapterRequest(roleIdSet));
    }

    @PostMapping(path = "/save/bound")
    public CompletableFuture<RoleBindingReport> interceptRoleBindingRequest(@RequestBody RoleBoundSavingAdapterRequest request){
        return roleDatabaseOperationsPipeline.runRoleBindingPipeline(request);
    }

    @DeleteMapping(path = "/del/bound")
    public CompletableFuture<RoleBoundsDeletingReport> interceptRoleBoundsDeletingRequest(@RequestBody Map<String, RoleBoundDeletingForm> roleBoundDeletingForms){
        return roleDatabaseOperationsPipeline.runRoleBoundsDeletingPipeline(new RoleBoundsDeletingAdapterRequest(roleBoundDeletingForms));
    }
}
