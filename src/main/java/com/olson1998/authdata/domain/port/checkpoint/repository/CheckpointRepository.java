package com.olson1998.authdata.domain.port.checkpoint.repository;

import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointTokenHolder;

import java.util.LinkedList;

public interface CheckpointRepository {

    LinkedList<String> getLogs();

    CheckpointTokenHolder create(Long expireTime, Integer maxUsages);

}
