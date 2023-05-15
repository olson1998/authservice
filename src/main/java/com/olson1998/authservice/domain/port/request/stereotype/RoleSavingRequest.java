package com.olson1998.authservice.domain.port.request.stereotype;

import com.olson1998.authservice.domain.port.request.data.RoleDetails;

import java.util.Set;

public interface RoleSavingRequest extends Request{

    Set<RoleDetails> getDetails();
}
