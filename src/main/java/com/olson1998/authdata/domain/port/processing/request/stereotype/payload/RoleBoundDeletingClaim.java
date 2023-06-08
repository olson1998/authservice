package com.olson1998.authdata.domain.port.processing.request.stereotype.payload;

import java.util.Set;

public interface RoleBoundDeletingClaim {

    boolean isDeleteAll();

    Set<String> getAuthoritiesIds();

}
