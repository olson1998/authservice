package com.olson1998.authservice.domain.port.request.data;

import com.olson1998.authservice.domain.port.request.utils.PersistType;

public interface PersistCommand {

    /**
     * Persist type command, available SAVE,BIND
     * @return persist type info
     */
    PersistType getPersistType();

    /**
     * Argument of command, if persist type specified as BIND argument will be processed as bind object id
     * @return
     */
    String getArgument();
}
