package com.olson1998.authdata.domain.port.processing.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@Getter
@RequiredArgsConstructor
public abstract class TenantDatasourceFabricatingException extends IllegalStateException {

    public abstract Logger getServiceLogger();

    @Override
    public abstract String getMessage();
}
