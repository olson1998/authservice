package com.olson1998.authdata.domain.port.processing.request.stereotype;

import java.util.Set;

public interface RoleDeletingRequest extends Request{

    Set<String> getRoleIdSet();
}
