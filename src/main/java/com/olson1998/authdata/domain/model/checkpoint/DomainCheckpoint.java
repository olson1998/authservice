package com.olson1998.authdata.domain.model.checkpoint;

import com.olson1998.authdata.domain.model.exception.checkpoint.CheckpointExpiredException;
import com.olson1998.authdata.domain.model.exception.checkpoint.CheckpointUsageExceedingException;
import com.olson1998.authdata.domain.model.exception.security.CheckpointTokenVerificationException;
import com.olson1998.authdata.domain.port.checkpoint.stereotype.Checkpoint;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

@Getter
public class DomainCheckpoint implements Checkpoint {

    private enum CheckpointStatus{
        CREATED,
        VERIFIED,
        EXPIRED,
        ERROR
    }

    private static final String BEARER = "Bearer ";

    private static final String CHECKPOINT_TOKEN_FORMAT = "Checkpoint(id=%s,tid=%s,uid=%s,tmp=%s,sign=%s)";

    private static final String TENANT_TOKEN_FORMAT = "Tenant(id=%s,tid=%s,sign=%s)";

    private static final String USER_TOKEN_FORMAT = "User(id=%s,tid=%s,cono=%s,uid=%s,sign=%s)";

    private static final String CHECKPOINT_LOG = "%s %s %s";

    private final UUID id;

    private final String tenantId;

    private final long userId;

    private final long timestamp = System.currentTimeMillis();

    private final Long expireTime;

    private final Integer maxUsageCount;

    private final LinkedList<String> logs = new LinkedList<>();

    private int usageCount = 0;

    @Override
    public boolean isExpiring() {
        return expireTime != null;
    }

    @Override
    public boolean isUsageCount() {
        return maxUsageCount != null;
    }

    @Override
    public LinkedList<String> getLogs() {
        return logs;
    }

    @Override
    public String writeCheckpointToken(@NonNull String sign) {
        var rawToken = String.format(
                CHECKPOINT_TOKEN_FORMAT,
                id,
                tenantId,
                userId,
                timestamp,
                sign
        );
        return digest(rawToken);
    }

    @Override
    public void verifyCheckpointToken(@NonNull String checkpointToken, @NonNull String sign) {
        verifyToken(checkpointToken, sign);
    }

    private String digest(String token){
        return DigestUtils.sha256Hex(token);
    }

    private void verifyToken(String xCheckpointToken, String secret){
        logs.add(log(CheckpointStatus.VERIFIED, "checkpoint verification"));
        var expectedToken = writeCheckpointToken(secret);
        if(isExpiring()){
            if(expireTime < System.currentTimeMillis()){
                var e = new CheckpointExpiredException(expireTime);
                logs.add(log(CheckpointStatus.EXPIRED, e.getHeaderValue()));
                throw e;
            }
        }
        if(isUsageCount()){
            usageCount++;
            if(usageCount > maxUsageCount){
                var e = new CheckpointUsageExceedingException(maxUsageCount);
                logs.add(log(CheckpointStatus.EXPIRED, e.getHeaderValue()));
                throw e;
            }
        }
        if(!expectedToken.equals(xCheckpointToken)){
            var e = new CheckpointTokenVerificationException();
            logs.add(log(CheckpointStatus.ERROR, e.getDisplayName()));
            throw e;
        }
    }

    private String log(CheckpointStatus status, String message){
        var now = LocalDateTime.now();
        return String.format(CHECKPOINT_LOG, status, now, message);
    }

    public DomainCheckpoint(UUID id, String tenantId, long userId, Long expireTime, Integer maxUsageCount) {
        this.id = id;
        this.tenantId = tenantId;
        this.userId = userId;
        this.expireTime = expireTime;
        this.maxUsageCount = maxUsageCount;
        this.logs.add(log(CheckpointStatus.CREATED, "checkpoint created"));
    }
}
