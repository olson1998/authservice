package com.olson1998.authdata.domain.service.pipeline;

import com.olson1998.authdata.domain.port.checkpoint.repository.CheckpointRepository;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointValues;
import com.olson1998.authdata.domain.port.pipeline.CheckpointPipeline;
import com.olson1998.authdata.domain.port.pipeline.PipelineFactory;
import com.olson1998.authdata.domain.service.checkpoint.CheckpointResponseEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class CheckpointPipelineService implements CheckpointPipeline {

    private final PipelineFactory pipelineFactory;

    private final CheckpointRepository checkpointRepository;

    private final CheckpointResponseEntityMapper checkpointResponseEntityMapper = new CheckpointResponseEntityMapper();

    @Override
    public CompletableFuture<LinkedList<String>> runGetCheckpointLogsPipeline() {
        return pipelineFactory.fabricate()
                .thenApplyAsync(empty -> checkpointRepository.getLogs())
                .thenApplyAsync(pipelineFactory::dematerializeContext);
    }

    @Override
    public CompletableFuture<ResponseEntity<CheckpointValues>> runCreateCheckpointPipeline(Long expireTime, Integer maxUsages) {
        return pipelineFactory.fabricate()
                .thenApplyAsync(empty -> checkpointRepository.create(expireTime, maxUsages))
                .thenApplyAsync(checkpointResponseEntityMapper::map)
                .thenApplyAsync(pipelineFactory::dematerializeContext);
    }


}
