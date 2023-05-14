package com.olson1998.authservice.domain.port.request.model;

import com.olson1998.authservice.domain.port.request.entity.RoleDetails;

import java.util.Set;

public interface RoleSavingRequest extends Request{

    Set<RoleDetails> getDetails();
}
