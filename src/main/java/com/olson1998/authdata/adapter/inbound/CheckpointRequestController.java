package com.olson1998.authdata.adapter.inbound;

import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointValues;
import com.olson1998.authdata.domain.port.pipeline.CheckpointPipeline;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor

@Async
@RestController
@RequestMapping(value = "/checkpoint")
public class CheckpointRequestController {

    private final CheckpointPipeline checkpointPipeline;

    @GetMapping(path = "/logs")
    public CompletableFuture<LinkedList<String>> getLogs(){
        return checkpointPipeline.runGetCheckpointLogsPipeline();
    }

    @PostMapping(path = "/create")
    public CompletableFuture<ResponseEntity<CheckpointValues>> createCheckpoint(@RequestParam(value = "expire", required = false) Long expireTime,
                                                                                @RequestParam(value = "max_usage", required = false) Integer maxUsage){
        return checkpointPipeline.runCreateCheckpointPipeline(expireTime, maxUsage);
    }
}
