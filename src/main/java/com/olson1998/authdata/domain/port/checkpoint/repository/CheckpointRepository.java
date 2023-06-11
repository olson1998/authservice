package com.olson1998.authdata.domain.port.checkpoint.repository;

import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointValues;
import org.springframework.http.ResponseEntity;

public interface CheckpointRepository {

    <B  extends CheckpointValues> ResponseEntity<B> create(Long expireTime, Integer maxUsages);

}
