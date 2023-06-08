package com.olson1998.authdata.domain.port.processing.request.stereotype;

import java.util.Map;
import java.util.Set;

public interface RoleBoundDeletingRequest extends Request{

    boolean isDeleteAll();

    Map<String, Set<String>> getRoleBoundsMap();

    void setDeleteAll(boolean deleteAll);
}
