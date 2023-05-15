package com.olson1998.authservice.domain.port.mapping.repository;

import com.olson1998.authservice.domain.port.data.entity.User;
import com.olson1998.authservice.domain.port.data.utils.ExtendedAuthorityTimestamp;
import com.olson1998.authservice.domain.port.mapping.stereotype.AuthoritiesTree;

import java.util.Set;

public interface AuthoritiesTreeMapper {

    AuthoritiesTree map(User user, Set<ExtendedAuthorityTimestamp> extendedAuthorityTimestamps);
}
