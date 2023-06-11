package com.olson1998.authdata.application.security.filter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class CommonResponseHeaders {

    public static final String CHECKPOINT_TOKEN_HEADER = "X-checkpoint-token";

    public static final String REQUEST_CONTEXT_ID = "X-request-id";

    public static final String REQUEST_CONTEXT_TENANT = "X-request-tenant";

    public static final String REQUEST_CONTEXT_USER = "X-request-user";

    public static final String CHECKPOINT_VERIFICATION_FAILURE = "X-checkpoint-verify";
}
