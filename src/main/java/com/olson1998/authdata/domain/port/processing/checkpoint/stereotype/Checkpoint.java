package com.olson1998.authdata.domain.port.processing.checkpoint.stereotype;

import java.util.List;

public interface Checkpoint {

    String getId();

    String getTenantId();

    long getCompanyNumber();

    long getUserId();

    long getTimestamp();

    Long getExpireTime();

    Integer getMaxUsageCount();

    boolean isExpiring();

    boolean isUsageCount();

    List<String> getLogs();

    String writeCheckpointToken(String sign);

    String writeTenantToken(String sign);

    String writeCompanyToken(String sign);

    String writeUserToken(String sign);

    void verifyCheckpointToken(String checkpointToken, String sign);

    void verifyUserToken(String userToken, String sign);

    void verifyTenantToken(String tenantToken, String sign);

    void verifyCompanyToken(String companyToken, String sign);
}
