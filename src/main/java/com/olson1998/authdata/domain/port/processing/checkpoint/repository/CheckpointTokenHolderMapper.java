package com.olson1998.authdata.domain.port.processing.checkpoint.repository;

import com.olson1998.authdata.domain.port.processing.checkpoint.stereotype.CheckpointTokenHolder;
import com.olson1998.authdata.domain.port.processing.checkpoint.stereotype.CheckpointValues;
import org.springframework.http.ResponseEntity;

public interface CheckpointTokenHolderMapper {

    <B extends CheckpointValues> ResponseEntity<B> mapToResponseEntity(CheckpointTokenHolder tokenHolder);
}
