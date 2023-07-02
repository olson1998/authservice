package com.olson1998.authdata.application.security.props;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.authdata.application.security.encoder")
public class PasswordEncodersProperties {

    private final Argon2 argon2 = new Argon2();

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Argon2{

        private int saltLength = 16;

        private int hashLength = 32;

        private int parallelism = 1;

        private int memory =16384;

        private int iterations = 2;
    }
}
