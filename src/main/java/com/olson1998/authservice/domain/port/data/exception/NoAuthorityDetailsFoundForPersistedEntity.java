package com.olson1998.authservice.domain.port.data.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class NoAuthorityDetailsFoundForPersistedEntity extends RollbackRequiredException {

    @Getter
    private final UUID requestId;

    @Override
    public String getMessage() {
        return "no user details found for given persisted entity";
    }
}
