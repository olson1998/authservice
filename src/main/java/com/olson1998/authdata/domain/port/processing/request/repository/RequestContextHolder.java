package com.olson1998.authdata.domain.port.processing.request.repository;

import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;

import java.util.UUID;

public interface RequestContextHolder {

    RequestContext getRequestContext();

    UUID getId();

    String getTenantId();

    long getUserId();

    void setCurrentContext(RequestContext requestContext);

    void clean();

}
