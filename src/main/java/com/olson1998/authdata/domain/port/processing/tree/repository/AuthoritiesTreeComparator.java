package com.olson1998.authdata.domain.port.processing.tree.repository;

import com.olson1998.authdata.domain.port.processing.tree.stereotype.AuthoritiesTree;

import java.util.Set;

public interface AuthoritiesTreeComparator {

    /**
     * Compares authorities if authority tree 1 contains role authorities from tree 2
     * @param authoritiesTree1  authorities tree 1
     * @param authoritiesTree2 authorities tree 2
     * @return Role id set with not present timestamps
     */
    Set<String> compare(AuthoritiesTree authoritiesTree1, AuthoritiesTree authoritiesTree2);
}
