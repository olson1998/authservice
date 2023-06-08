package com.olson1998.authdata.domain.port.data.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import java.util.UUID;

@RequiredArgsConstructor
public class NoAuthorityDetailsFoundForPersistedEntity extends RollbackRequiredException {

    @Getter
    private final Logger serviceLogger;

    @Getter
    private final UUID requestId;

    @Override
    public String getMessage() {
        return "no user details found for given persisted entity";
    }
}
