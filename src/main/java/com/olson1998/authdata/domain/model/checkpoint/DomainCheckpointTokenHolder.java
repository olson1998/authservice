package com.olson1998.authdata.domain.model.checkpoint;

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

    @JsonProperty(value = "tid", required = true)
    private final String tenantId;

    @JsonProperty(value = "exp_time")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Long expireTime;

    @JsonProperty(value = "max_usage")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final Integer maxUsageCount;

    public DomainCheckpointTokenHolder(Checkpoint checkpoint, Algorithm algorithm) {
        this.checkpointToken = checkpoint.writeCheckpointToken(algorithm.toString());
        this.tenantId = checkpoint.getTenantId();
        this.expireTime = checkpoint.getExpireTime();
        this.maxUsageCount = checkpoint.getMaxUsageCount();
    }
}
