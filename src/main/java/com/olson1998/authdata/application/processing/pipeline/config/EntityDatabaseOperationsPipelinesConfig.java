package com.olson1998.authdata.application.processing.pipeline.config;

import com.olson1998.authdata.application.processing.pipeline.ApplicationPipelineFactory;
import com.olson1998.authdata.domain.port.pipeline.RoleDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.pipeline.UserDatabaseOperationsPipeline;
import com.olson1998.authdata.domain.port.processing.request.repository.RoleRequestProcessor;
import com.olson1998.authdata.domain.port.processing.request.repository.UserRequestProcessor;
import com.olson1998.authdata.domain.service.pipeline.RoleDataPipelineService;
import com.olson1998.authdata.domain.service.pipeline.UserDataPipelineService;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityDatabaseOperationsPipelinesConfig {

    @Bean
    public UserDatabaseOperationsPipeline userDatabaseOperationsPipeline(@NonNull UserRequestProcessor userRequestProcessor,
                                                                         @NonNull ApplicationPipelineFactory applicationPipelineFactory){
        return new UserDataPipelineService(
                applicationPipelineFactory,
                userRequestProcessor
        );
    }

    @Bean
    public RoleDatabaseOperationsPipeline roleDatabaseOperationsPipeline(@NonNull RoleRequestProcessor roleRequestProcessor,
                                                                         @NonNull ApplicationPipelineFactory applicationPipelineFactory){
        return new RoleDataPipelineService(
                applicationPipelineFactory,
                roleRequestProcessor
        );
    }
}
