package com.olson1998.authservice.domain.port.request.model;

import com.olson1998.authservice.domain.port.request.entity.UserDetails;

public interface UserSavingRequest {

    UserDetails getUserDetails();
}
