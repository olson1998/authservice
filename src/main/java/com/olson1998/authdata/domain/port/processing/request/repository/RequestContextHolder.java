package com.olson1998.authdata.domain.port.processing.request.repository;

import com.olson1998.authdata.domain.port.processing.request.stereotype.RequestContext;

public interface RequestContextHolder {

    RequestContext getLocalThreadContext();
}
