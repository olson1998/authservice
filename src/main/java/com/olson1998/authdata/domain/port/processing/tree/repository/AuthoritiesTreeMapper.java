package com.olson1998.authdata.domain.port.processing.tree.repository;

import com.olson1998.authdata.domain.port.data.stereotype.User;
import com.olson1998.authdata.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.AuthoritiesTree;

import java.util.Set;

public interface AuthoritiesTreeMapper {

    AuthoritiesTree map(User user, Set<ExtendedAuthorityTimestamp> extendedAuthorityTimestamps);
}
