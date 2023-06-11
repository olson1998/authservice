package com.olson1998.authdata.application.pipeline.config;

import com.olson1998.authdata.application.pipeline.ApplicationPipelineFactory;
import com.olson1998.authdata.domain.port.checkpoint.repository.CheckpointRepository;
import com.olson1998.authdata.domain.port.pipeline.CheckpointPipeline;
import com.olson1998.authdata.domain.service.pipeline.CheckpointPipelineService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CheckpointPipelineFactory {

    @Bean
    public CheckpointPipeline checkpointPipeline(ApplicationPipelineFactory applicationPipelineFactory,
                                                 CheckpointRepository checkpointRepository
                                                 ){
        return new CheckpointPipelineService(
                applicationPipelineFactory,
                checkpointRepository
        );
    }
}
