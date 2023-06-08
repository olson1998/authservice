package com.olson1998.authdata.domain.service.processing.request;

import com.olson1998.authdata.domain.port.data.stereotype.*;
import com.olson1998.authdata.domain.port.processing.request.stereotype.Request;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ProcessingRequestLogger {

    private static final String REQUEST_PROCESSING_LOG_FORMAT =
            "Intercepted request '{}' to {} {}";

    protected static void log(Logger log, Request request, RequestType requestType, Class<?> clazz){
        log.info(REQUEST_PROCESSING_LOG_FORMAT, request.getId(), requestType.displayName, displayNameOf(clazz));
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    protected enum RequestType{
        SAVE("save"),
        DELETE("delete");

        private final String displayName;
    }

    private static String displayNameOf(Class<?> clazz){
        String displayName = clazz.getSimpleName();
        if (User.class.equals(clazz)) {
            displayName = "user";
        } else if (UserMembership.class.equals(clazz)) {
            displayName = "user membership";
        } else if (Role.class.equals(clazz)) {
            displayName = "role";
        } else if (RoleBinding.class.equals(clazz)) {
            displayName = "role binding";
        } else if (Authority.class.equals(clazz)){
            displayName = "authority";
        }
        return displayName;
    }
}
