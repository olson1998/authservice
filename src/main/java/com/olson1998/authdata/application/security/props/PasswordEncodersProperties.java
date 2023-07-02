package com.olson1998.authdata.application.security.props;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.authdata.application.security.encoder")
public class PasswordEncodersProperties {

    private Argon2 argon2 = new Argon2();

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Argon2{

        private int saltLength;

        private int hashLength;

        private int length;

        private int parallelism;

        private int memory;

        private int iterations;
    }
}
