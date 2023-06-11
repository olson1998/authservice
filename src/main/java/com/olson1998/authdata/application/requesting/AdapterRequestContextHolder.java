package com.olson1998.authdata.application.requesting;

import com.olson1998.authdata.domain.port.processing.request.repository.RequestContextHolder;
import com.olson1998.authdata.domain.port.security.stereotype.RequestContext;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AdapterRequestContextHolder implements RequestContextHolder {

    private static final ThreadLocal<RequestContext> THREAD_LOCAL_REQUEST_CONTEXT_HOLDER = new ThreadLocal<>();

    public static RequestContext getLocalThreadRequestContext(){
        return THREAD_LOCAL_REQUEST_CONTEXT_HOLDER.get();
    }

    public static UUID getLocalThreadRequestId(){
        return THREAD_LOCAL_REQUEST_CONTEXT_HOLDER.get().getId();
    }

    public static String getLocalThreadTenantId(){
        return THREAD_LOCAL_REQUEST_CONTEXT_HOLDER.get().getTenantId();
    }

    public static long getLocalThreadUserId(){
        return THREAD_LOCAL_REQUEST_CONTEXT_HOLDER.get().getUserId();
    }

    public static void setLocalThreadRequestContext(RequestContext requestContext){
        THREAD_LOCAL_REQUEST_CONTEXT_HOLDER.set(requestContext);
    }

    public static void cleanContext(){
        THREAD_LOCAL_REQUEST_CONTEXT_HOLDER.set(null);
    }

    @Override
    public RequestContext getRequestContext() {
        return getLocalThreadRequestContext();
    }

    @Override
    public UUID getId() {
        return getLocalThreadRequestId();
    }

    @Override
    public String getTenantId() {
        return getLocalThreadTenantId();
    }

    @Override
    public long getUserId() {
        return getLocalThreadUserId();
    }
}
