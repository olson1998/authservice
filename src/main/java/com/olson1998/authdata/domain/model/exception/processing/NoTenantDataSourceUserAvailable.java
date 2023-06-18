package com.olson1998.authdata.domain.model.exception.processing;

import com.olson1998.authdata.domain.port.processing.exception.TenantDatasourceFabricatingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@Getter
@RequiredArgsConstructor
public class NoTenantDataSourceUserAvailable extends TenantDatasourceFabricatingException {

    private final Logger serviceLogger;

    private final String tid;

    @Override
    public String getMessage() {
        return String.join("No datasource found for tenant: ", tid);
    }
}
