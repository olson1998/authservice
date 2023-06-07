package com.olson1998.authdata.domain.service.processing.tree;

import com.olson1998.authdata.domain.model.exception.processing.MismatchAuthoritiesTreeUserIdException;
import com.olson1998.authdata.domain.port.processing.tree.repository.AuthoritiesTreeComparator;
import com.olson1998.authdata.domain.port.processing.tree.stereotype.AuthoritiesTree;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public class DomainAuthoritiesTreeComparator implements AuthoritiesTreeComparator {

    @Override
    public Set<String> compare(@NonNull AuthoritiesTree authoritiesTree1, @NonNull AuthoritiesTree authoritiesTree2) {
        checkUserId(authoritiesTree1, authoritiesTree2);
        var missingRoleTimestampsIds = new HashSet<String>();
        var authoritiesTree1RoleTimestamps = authoritiesTree1.getRoleTimestamps();
        var authoritiesTree2RoleTimestamps = authoritiesTree2.getRoleTimestamps();
        authoritiesTree2RoleTimestamps.forEach(roleTimestamp -> {
            if(!authoritiesTree1RoleTimestamps.contains(roleTimestamp)){
                missingRoleTimestampsIds.add(roleTimestamp.getId());
            }
        });
        return missingRoleTimestampsIds;
    }

    private void checkUserId(AuthoritiesTree authoritiesTree1, AuthoritiesTree authoritiesTree2){
        if(authoritiesTree1.getUserId() != authoritiesTree2.getUserId()){
            throw new MismatchAuthoritiesTreeUserIdException();
        }
    }
}
