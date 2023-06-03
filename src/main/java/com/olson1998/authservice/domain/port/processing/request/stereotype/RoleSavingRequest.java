package com.olson1998.authservice.domain.port.processing.request.stereotype;

import com.olson1998.authservice.domain.port.processing.request.stereotype.payload.RoleDetails;

import java.util.Set;

public interface RoleSavingRequest extends Request{

    Set<RoleDetails> getDetails();
}
