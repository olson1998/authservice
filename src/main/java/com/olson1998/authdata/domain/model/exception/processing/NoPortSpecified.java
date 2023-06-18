package com.olson1998.authdata.domain.model.exception.processing;

import com.olson1998.authdata.domain.port.processing.exception.TenantDatasourceFabricatingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import javax.sql.DataSource;

@Getter
@RequiredArgsConstructor
public class NoPortSpecified extends TenantDatasourceFabricatingException {

    private final Logger serviceLogger;

    private final Class<? extends DataSource> type;

    private final String tid;

    @Override
    public String getMessage() {
        return String.format("tenant '%s' data source of type: '%s' requires port specified",tid, type.getName());
    }
}
