package com.olson1998.authdata.application.security.filter;

import com.olson1998.authdata.domain.port.security.repository.CheckpointAuthenticationConverter;
import com.olson1998.authdata.domain.port.security.repository.CheckpointAuthenticationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Slf4j
public class CheckpointAuthenticationFilter extends AuthenticationFilter {

    public CheckpointAuthenticationFilter(CheckpointAuthenticationManager checkpointAuthenticationManager,
                                          CheckpointAuthenticationConverter checkpointAuthenticationConverter) {
        super(checkpointAuthenticationManager, checkpointAuthenticationConverter);
    }
}
