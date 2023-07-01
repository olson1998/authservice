package com.olson1998.authdata.application.pipeline.config;

import com.olson1998.authdata.application.pipeline.PipelineService;
import com.olson1998.authdata.domain.port.pipeline.AuthDataPipeline;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthUserRequestProcessor;
import com.olson1998.authdata.domain.service.pipeline.AuthDataPipelineService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthDataPipelineConfig {

    @Bean
    public AuthDataPipeline authDataPipeline(@NonNull PipelineService applicationPipelineFactory,
                                             @NonNull AuthUserRequestProcessor authUserRequestProcessor){
        return new AuthDataPipelineService(
                applicationPipelineFactory,
                authUserRequestProcessor
        );
    }
}
