package com.olson1998.authdata.domain.service.checkpoint;

import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointTokenHolder;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointValues;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@NoArgsConstructor
public class CheckpointResponseEntityMapper {

    private static final String CHECKPOINT_TOKEN_HEADER = "X-Checkpoint-token";

    private static final String TENANT_TOKEN_HEADER = "X-tenant-token";

    private static final String USER_TOKEN_HEADER = "X-user-token";

    public ResponseEntity<CheckpointValues> map(@NonNull CheckpointTokenHolder tokenHolder) {
        var bodyBuilder = ResponseEntity.accepted();
        bodyBuilder.header(CHECKPOINT_TOKEN_HEADER, tokenHolder.getCheckpointToken());
        bodyBuilder.contentType(APPLICATION_JSON);
        return bodyBuilder.body(tokenHolder);
    }
}
