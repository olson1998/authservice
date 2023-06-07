package com.olson1998.authdata.application.datasource.properties;

import com.olson1998.authdata.application.datasource.entity.utils.SecretDigest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.olson1998.authdata.application.datasource.entity.utils.SecretDigest.DEFAULT_DIGEST;

@Setter

@Configuration
@ConfigurationProperties(prefix = "olson1998.authservice.datasource.main")
public class MainDatasourceProperties {

    @Getter
    private String description = "main datasource";

    @Getter
    private String user ="user";

    private String password = null;

    private SecretDigest secretDigest = DEFAULT_DIGEST;

    @Getter
    private String url ="main-datasource";

    @Getter
    private int port =1433;

    @Getter
    private String database ="db";

    @Getter
    private String schema;

    @Getter
    private boolean trustCertificate = true;

    @Getter
    private int loginTimeout = 30;

    public String getEncryptedPassword(){
        return secretDigest.encrypt(password);
    }
}
