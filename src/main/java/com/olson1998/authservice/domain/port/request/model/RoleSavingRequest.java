package com.olson1998.authservice.domain.port.request.model;

import com.olson1998.authservice.domain.port.request.entity.RoleDetails;

public interface RoleSavingRequest extends Request{

    RoleDetails getDetails();
}
