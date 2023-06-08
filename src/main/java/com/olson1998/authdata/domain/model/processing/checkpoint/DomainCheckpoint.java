package com.olson1998.authdata.domain.model.processing.checkpoint;

import com.olson1998.authdata.domain.model.exception.checkpoint.CheckpointExpiredException;
import com.olson1998.authdata.domain.model.exception.checkpoint.CheckpointTokenVerificationException;
import com.olson1998.authdata.domain.model.exception.checkpoint.CheckpointUsageExceedingException;
import com.olson1998.authdata.domain.port.processing.checkpoint.stereotype.Checkpoint;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

@Getter
public class DomainCheckpoint implements Checkpoint {

    private enum CheckpointStatus{
        CREATED,
        VERIFIED,
        EXPIRED,
        ERROR
    }

    private static final String BEARER = "Bearer ";

    private static final MessageDigest TOKEN_DIGEST = DigestUtils.getSha3_256Digest();

    private static final String CHECKPOINT_TOKEN_FORMAT = "Checkpoint(id=%s,tid=%s,cono=%s,uid=%s,tmp=%s,sign=%s)";

    private static final String TENANT_TOKEN_FORMAT = "Tenant(id=%s,tid=%s,tmp=%s,sign=%s)";

    private static final String COMPANY_TOKEN_FORMAT = "Company(id=%s,tid=%s,cono=%s,tmp=%s,sign=%s)";

    private static final String USER_TOKEN_FORMAT = "User(id=%s,tid=%s,cono=%s,uid=%s,tmp=%s,sign=%s)";

    private static final String CHECKPOINT_LOG = "%s %s %s";

    private final String id = "CHECKPOINT&" + randomAlphanumeric(15);

    private final String tenantId;

    private final long companyNumber;

    private final long userId;

    private final long timestamp = System.currentTimeMillis();

    private final Long expireTime;

    private final Integer maxUsageCount;

    private final List<String> logs = new LinkedList<>();

    private int usageCount = 0;

    @Override
    public boolean isExpiring() {
        return maxUsageCount != null;
    }

    @Override
    public boolean isUsageCount() {
        return maxUsageCount != null;
    }

    @Override
    public List<String> getLogs(@NonNull String token,@NonNull String sign) {
        var expected = writeCheckpointToken(sign);
        if(expected.equals(token)){
            return logs;
        }else {
            throw new CheckpointTokenVerificationException();
        }
    }

    @Override
    public String writeCheckpointToken(@NonNull String sign) {
        var rawToken = String.format(
                CHECKPOINT_TOKEN_FORMAT,
                id,
                tenantId,
                companyNumber,
                userId,
                timestamp,
                sign
        );
        return digest(rawToken);
    }

    @Override
    public String writeTenantToken(@NonNull String sign) {
        var rawToken = String.format(
                TENANT_TOKEN_FORMAT,
                id,
                tenantId,
                timestamp,
                sign
        );
        return digest(rawToken);
    }

    @Override
    public String writeCompanyToken(@NonNull String sign) {
        var rawToken = String.format(
                COMPANY_TOKEN_FORMAT,
                id,
                tenantId,
                companyNumber,
                timestamp,
                sign
        );
        return digest(rawToken);
    }

    @Override
    public String writeUserToken(@NonNull String sign) {
        var rawToken = String.format(
                USER_TOKEN_FORMAT,
                id,
                tenantId,
                companyNumber,
                userId,
                timestamp,
                sign
        );
        return digest(rawToken);
    }

    @Override
    public void verifyCheckpointToken(@NonNull String checkpointToken, @NonNull String sign) {
        verifyToken(writeCheckpointToken(sign), checkpointToken);
    }

    @Override
    public void verifyUserToken(@NonNull String userToken, @NonNull String sign) {
        verifyToken(writeUserToken(sign), userToken);
    }

    @Override
    public void verifyTenantToken(@NonNull String tenantToken, @NonNull String sign) {
        verifyToken(writeTenantToken(tenantToken), tenantToken);
    }

    @Override
    public void verifyCompanyToken(@NonNull String companyToken, @NonNull String sign) {
        verifyToken(writeCompanyToken(companyToken), companyToken);
    }

    private String digest(String token){
        return new String(TOKEN_DIGEST.digest(token.getBytes(UTF_8)), UTF_8);
    }

    private void verifyToken(String expectedToken, String givenToken){
        givenToken = StringUtils.substringAfter(givenToken, BEARER);
        logs.add(log(CheckpointStatus.VERIFIED, "checkpoint verification"));
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
        if(!expectedToken.equals(givenToken)){
            var e = new CheckpointTokenVerificationException();
            logs.add(log(CheckpointStatus.ERROR, e.getHeaderValue()));
            throw e;
        }
    }

    private String log(CheckpointStatus status, String message){
        var now = LocalDateTime.now();
        return String.format(CHECKPOINT_LOG, now, status, message);
    }

    public DomainCheckpoint(String tenantId, long companyNumber, long userId, Long expireTime, Integer maxUsageCount) {
        this.tenantId = tenantId;
        this.companyNumber = companyNumber;
        this.userId = userId;
        this.expireTime = expireTime;
        this.maxUsageCount = maxUsageCount;
        this.logs.add(log(CheckpointStatus.CREATED, "checkpoint created"));
    }
}
