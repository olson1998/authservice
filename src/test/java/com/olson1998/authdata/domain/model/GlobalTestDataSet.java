package com.olson1998.authdata.domain.model;

import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.net.InetAddress;
import java.util.UUID;

import static com.olson1998.authdata.application.datasource.entity.UserTestDataSet.TEST_USER_ID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class GlobalTestDataSet {

    public static final String TEST_TENANT_ID= "TEST_TENANT";

    public static final UUID TEST_CHECKPOINT_ID = UUID.randomUUID();

    public static final RequestContext TEST_REQUEST_CONTEXT = new RequestContext() {
        @Override
        public UUID getId() {
            return TEST_CHECKPOINT_ID;
        }

        @Override
        public String getTenantId() {
            return TEST_TENANT_ID;
        }

        @Override
        public long getUserId() {
            return TEST_USER_ID;
        }
    };
}
