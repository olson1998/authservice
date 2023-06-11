package com.olson1998.authdata.domain.model.processing.checkpoint;

import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.CheckpointTokenHolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DomainCheckpointTokenHolder implements CheckpointTokenHolder {

    @JsonIgnore
    private final String checkpointToken;

    @JsonIgnore
    private final String tenantToken;

    @JsonIgnore
    private final String userToken;

    @JsonProperty(value = "tid", required = true)
    private final String tenantId;

    @JsonProperty(value = "timestamp", required = true)
    private final long timestamp;

    @JsonProperty(value = "exp_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long expireTime;

    @JsonProperty(value = "max_usage")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer maxUsageCount;

    public DomainCheckpointTokenHolder(Checkpoint checkpoint, Algorithm algorithm) {
        this.checkpointToken = checkpoint.writeCheckpointToken(algorithm.toString());
        this.tenantToken = checkpoint.writeTenantToken(algorithm.toString());
        this.userToken = checkpoint.writeUserToken(algorithm.toString());
        this.tenantId = checkpoint.getTenantId();
        this.timestamp = checkpoint.getTimestamp();
        this.expireTime = checkpoint.getExpireTime();
        this.maxUsageCount = checkpoint.getMaxUsageCount();
    }
}