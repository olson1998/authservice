package com.olson1998.authservice.domain.service.processing.request;

import com.olson1998.authservice.domain.port.processing.request.stereotype.Request;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ProcessingRequestLogger {

    private static final String REQUEST_PROCESSING_LOG_FORMAT =
            "Intercepted request '{}' to {} {}";

    protected static void log(Logger log, Request request, RequestType requestType, Class<?> clazz){
        log.info(REQUEST_PROCESSING_LOG_FORMAT, request.getId(), requestType.displayName, clazz.getSimpleName());
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    protected enum RequestType{
        SAVE("save"),
        DELETE("delete");

        private final String displayName;
    }
}
