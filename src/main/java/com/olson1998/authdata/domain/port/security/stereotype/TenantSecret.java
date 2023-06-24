package com.olson1998.authdata.domain.port.security.stereotype;

import com.olson1998.authdata.domain.port.data.stereotype.TenantAlgorithm;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.UserDetails;

public interface TenantSecret extends TenantAlgorithm, UserDetails {

    String[] getTrustedIssuers();
}
