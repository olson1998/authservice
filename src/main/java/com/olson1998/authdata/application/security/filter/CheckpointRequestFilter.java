package com.olson1998.authdata.application.security.filter;

import com.olson1998.authdata.domain.port.security.repository.CheckpointAuthenticationConverter;
import com.olson1998.authdata.domain.port.security.repository.CheckpointRequestManager;
import org.springframework.security.web.authentication.AuthenticationFilter;

public class CheckpointRequestFilter extends AuthenticationFilter {

    public CheckpointRequestFilter(CheckpointRequestManager checkpointRequestManager,
                                   CheckpointAuthenticationConverter checkpointAuthenticationConverter) {
        super(checkpointRequestManager, checkpointAuthenticationConverter);
    }
}
