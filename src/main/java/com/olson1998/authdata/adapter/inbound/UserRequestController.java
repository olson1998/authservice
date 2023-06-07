package com.olson1998.authdata.adapter.inbound;

import com.olson1998.authdata.application.requesting.model.UserMembershipBindAdapterRequest;
import com.olson1998.authdata.application.requesting.model.UserSavingAdapterRequest;
import com.olson1998.authdata.domain.port.processing.pipeline.UserDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserDeletingReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserMembershipBindReport;
import com.olson1998.authdata.domain.port.processing.report.stereotype.UserSavingReport;
import com.olson1998.authdata.domain.port.processing.request.stereotype.UserDeletingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Async
@RestController
@RequestMapping(value = "/user/data")
public class UserRequestController {

    private final UserDatabaseOperationsPipeline userDatabaseOperationsPipeline;

    @PostMapping(path = "/post")
    public CompletableFuture<UserSavingReport> interceptUserSavingRequest(@RequestBody UserSavingAdapterRequest userSavingAdapterRequest){
        return userDatabaseOperationsPipeline.runSaveUserPipeline(userSavingAdapterRequest);
    }

    @PostMapping(path = "/post/membership")
    public CompletableFuture<UserMembershipBindReport> interceptUserMembershipSavingReport(@RequestBody UserMembershipBindAdapterRequest request){
        return userDatabaseOperationsPipeline.runUserMembershipBindPipeline(request);
    }

    @DeleteMapping(path = "/del")
    public CompletableFuture<UserDeletingReport> interceptUserDeletingRequest(@RequestBody UserDeletingRequest userDeletingRequest){
        return userDatabaseOperationsPipeline.runUserDeletePipeline(userDeletingRequest);
    }
}
