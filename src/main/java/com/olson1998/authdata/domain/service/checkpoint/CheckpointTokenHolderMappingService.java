package com.olson1998.authdata.domain.service.checkpoint;

import com.olson1998.authdata.domain.port.checkpoint.repository.CheckpointTokenHolderMapper;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointTokenHolder;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointValues;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;

public class CheckpointTokenHolderMappingService implements CheckpointTokenHolderMapper {

    private static final String CHECKPOINT_TOKEN_HEADER = "X-checkpoint-token";

    private static final String TENANT_TOKEN_HEADER = "X-tenant-token";

    private static final String USER_TOKEN_HEADER = "X-user-token";

    @Override
    public <B extends CheckpointValues> ResponseEntity<B> mapToResponseEntity(@NonNull CheckpointTokenHolder tokenHolder) {
        var bodyBuilder = ResponseEntity.ok();
        bodyBuilder.header(CHECKPOINT_TOKEN_HEADER, tokenHolder.getCheckpointToken());
        bodyBuilder.header(TENANT_TOKEN_HEADER, tokenHolder.getTenantToken());
        bodyBuilder.header(USER_TOKEN_HEADER, tokenHolder.getUserToken());
        bodyBuilder.body(tokenHolder);
        return bodyBuilder.build();
    }
}
