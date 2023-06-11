package com.olson1998.authdata.domain.port.checkpoint.stereotype;

import java.util.List;
import java.util.UUID;

public interface Checkpoint extends CheckpointValues{

    UUID getId();

    long getUserId();

    boolean isExpiring();

    boolean isUsageCount();

    List<String> getLogs(String token, String sign);

    String writeCheckpointToken(String sign);

    String writeTenantToken(String sign);

    String writeUserToken(String sign);

    void verifyCheckpointToken(String checkpointToken, String sign);

    void verifyUserToken(String userToken, String sign);

    void verifyTenantToken(String tenantToken, String sign);

}
