package com.olson1998.authdata.application.pipeline.config;

import com.olson1998.authdata.domain.service.pipeline.PipelineService;
import com.olson1998.authdata.domain.port.pipeline.repository.AuthorityDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.pipeline.repository.RoleDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.pipeline.repository.UserDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.request.repository.AuthorityRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authdata.domain.service.pipeline.AuthorityDataPipelineService;
import com.olson1998.authdata.domain.service.pipeline.RoleDataPipelineService;
import com.olson1998.authdata.domain.service.pipeline.UserDataPipelineService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityDatabaseOperationsPipelinesConfig {

    @Bean
    public UserDatabaseOperationsPipeline userDatabaseOperationsPipeline(@NonNull UserRequestProcessor userRequestProcessor,
                                                                         @NonNull PipelineService applicationPipelineFactory){
        return new UserDataPipelineService(
                applicationPipelineFactory,
                userRequestProcessor
        );
    }

    @Bean
    public RoleDatabaseOperationsPipeline roleDatabaseOperationsPipeline(@NonNull RoleRequestProcessor roleRequestProcessor,
                                                                         @NonNull PipelineService applicationPipelineFactory){
        return new RoleDataPipelineService(
                applicationPipelineFactory,
                roleRequestProcessor
        );
    }

    @Bean
    public AuthorityDatabaseOperationsPipeline authorityDatabaseOperationsPipeline(@NonNull AuthorityRequestProcessor authorityRequestProcessor,
                                                                                   @NonNull PipelineService applicationPipelineFactory){
        return new AuthorityDataPipelineService(
                applicationPipelineFactory,
                authorityRequestProcessor
        );
    }

}
