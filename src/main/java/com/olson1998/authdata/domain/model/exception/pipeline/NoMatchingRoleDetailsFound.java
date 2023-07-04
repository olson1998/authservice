package com.olson1998.authdata.domain.model.exception.pipeline;

import com.olson1998.authdata.domain.port.data.exception.RollbackRequiredException;
import com.olson1998.authdata.domain.port.data.stereotype.Role;
import lombok.Getter;
import org.slf4j.Logger;

import java.util.UUID;

@Getter
public class NoMatchingRoleDetailsFound extends RollbackRequiredException {

    private final UUID requestId;

    private final Logger serviceLogger;

    private final String message;

    public NoMatchingRoleDetailsFound(Logger serviceLogger, UUID requestId, Role role) {
        this.serviceLogger = serviceLogger;
        this.requestId = requestId;
        this.message = String.format(
                "No matching role found for role: '%s' with name: '%s'",
                role.getId(),
                role.getName()
        );
    }

}
