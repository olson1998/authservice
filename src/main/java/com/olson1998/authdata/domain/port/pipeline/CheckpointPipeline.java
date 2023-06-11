package com.olson1998.authdata.domain.port.pipeline;

import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointValues;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

public interface CheckpointPipeline {

    CompletableFuture<LinkedList<String>> runGetCheckpointLogsPipeline();

    CompletableFuture<ResponseEntity<CheckpointValues>> runCreateCheckpointPipeline(Long expireTime, Integer maxUsages);
}
