package com.olson1998.authdata.application.pipeline.exception;

import com.olson1998.authdata.domain.port.processing.exception.PipelineRuntimeException;
import lombok.Getter;
import org.slf4j.Logger;

import java.sql.SQLException;

@Getter
public class CouldNotEstablishConnectionToTenantDataSource extends PipelineRuntimeException {

    private final Logger serviceLogger;

    private final String message;

    public CouldNotEstablishConnectionToTenantDataSource(Logger serviceLogger, String tenant) {
        this.serviceLogger = serviceLogger;
        this.message = String.format("Couldn't connect to '%s' tenant database", tenant);
    }

    public CouldNotEstablishConnectionToTenantDataSource(Logger serviceLogger, SQLException e) {
        this.serviceLogger = serviceLogger;
        this.message = "Could not connect to database reason: " + e.getMessage();
    }
}
