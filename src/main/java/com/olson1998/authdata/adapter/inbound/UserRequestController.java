package com.olson1998.authdata.adapter.inbound;

import com.olson1998.authdata.application.requesting.model.UserDeletingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.UserMembershipSavingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.UserMembershipDeletingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.UserSavingAdapterRequest;
import com.olson1998.authdata.application.requesting.model.payload.UserMembershipForm;
import com.olson1998.authdata.domain.port.pipeline.UserDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipBindReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserSavingReport;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Async
@RestController
@RequestMapping(value = "/user/data")
public class UserRequestController {

    private final UserDatabaseOperationsPipeline userDatabaseOperationsPipeline;

    @PostMapping(path = "/save")
    public CompletableFuture<UserSavingReport> interceptUserSavingRequest(@RequestBody UserSavingAdapterRequest userSavingAdapterRequest){
        return userDatabaseOperationsPipeline.runSaveUserPipeline(userSavingAdapterRequest);
    }

    @PostMapping(path = "/save/mb")
    public CompletableFuture<UserMembershipBindReport> interceptUserMembershipSavingReport(@RequestBody Set<UserMembershipForm> userMembershipForms){
        return userDatabaseOperationsPipeline.runUserMembershipBindPipeline(new UserMembershipSavingAdapterRequest(userMembershipForms));
    }

    @DeleteMapping(path = "/del")
    public CompletableFuture<UserDeletingReport> interceptUserDeletingRequest(){
        return userDatabaseOperationsPipeline.runUserDeletePipeline(new UserDeletingAdapterRequest());
    }

    @DeleteMapping(path = "/del/mb")
    public CompletableFuture<UserMembershipDeletingReport> interceptUserMembershipDeletingRequest(@RequestBody UserMembershipDeletingAdapterRequest request){
        return userDatabaseOperationsPipeline.runUserMembershipDeletingPipeline(request);
    }
}
