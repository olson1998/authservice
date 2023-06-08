package com.olson1998.authdata.application.processing.config.tree;

import com.olson1998.authdata.domain.port.processing.tree.repository.AuthoritiesTreeComparator;
import com.olson1998.authdata.domain.port.processing.tree.repository.AuthoritiesTreeMapper;
import com.olson1998.authdata.domain.service.processing.tree.DomainAuthoritiesTreeComparator;
import com.olson1998.authdata.domain.service.processing.tree.DomainAuthoritiesTreeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthoritiesTreeTimestampsTreeConfig {

    @Bean
    public AuthoritiesTreeMapper authoritiesTreeMapper(){
        return new DomainAuthoritiesTreeMapper();
    }

    @Bean
    public AuthoritiesTreeComparator authoritiesTreeComparator(){
        return new DomainAuthoritiesTreeComparator();
    }
}
