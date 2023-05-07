package com.olson1998.authservice.application.datasource.properties;

import com.olson1998.authservice.application.datasource.entity.PasswordDigest;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.olson1998.authservice.application.datasource.entity.PasswordDigest.DEFAULT_DIGEST;
import static java.nio.charset.StandardCharsets.UTF_8;

@Setter

@Configuration
@ConfigurationProperties(prefix = "olson1998.authservice.datasource.main")
public class MainDatasourceProperties {

    @Getter
    private String description = "main datasource";

    @Getter
    private String user ="user";

    private String password = null;

    private PasswordDigest passwordDigest = DEFAULT_DIGEST;

    @Getter
    private String url ="main-datasource";

    @Getter
    private int port =1433;

    @Getter
    private String database ="db";

    @Getter
    private boolean trustCertificate = true;

    @Getter
    private int loginTimeout = 30;

    public String getEncryptedPassword(){
        var encPassBytes = DigestUtils.digest(
                passwordDigest.toMessageDigest(),
                password.getBytes(UTF_8)
        );
        return new String(encPassBytes, UTF_8);
    }
}
