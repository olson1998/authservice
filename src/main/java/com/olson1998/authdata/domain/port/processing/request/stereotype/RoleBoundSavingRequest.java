package com.olson1998.authdata.domain.port.processing.request.stereotype;

import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.AuthorityDetails;
import com.olson1998.authdata.domain.port.processing.request.stereotype.payload.RoleBindingClaim;

import java.util.Map;
import java.util.Set;

public interface RoleBoundSavingRequest extends Request {

    Set<RoleBindingClaim> getRolesBindingsClaims();

    Map<String, Set<AuthorityDetails>> getRoleIdAuthoritySavingRequestMap();
}
