package com.olson1998.authdata.domain.port.processing.request.stereotype;

import java.util.UUID;

public interface RequestContext {

    UUID getId();

    String getTenantId();

    long getUserId();
}
