package com.olson1998.authdata.domain.port.security.stereotype;

import com.olson1998.authdata.domain.port.data.stereotype.TenantAlgorithm;

public interface TenantSecret extends TenantAlgorithm {

    String[] getTrustedIssuers();
}
