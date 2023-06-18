package com.olson1998.authdata.application.datasource.properties;

import com.olson1998.authdata.application.datasource.entity.global.values.SqlDataSource;
import com.olson1998.authdata.application.datasource.entity.tenant.values.SecretDigest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static com.olson1998.authdata.application.datasource.entity.tenant.values.SecretDigest.NONE;

@Setter

@Configuration
@ConfigurationProperties(prefix = "com.olson1998.authdata.global.datasource")
public class GlobalDatasourceProperties {

    @Getter
    private SqlDataSource type;

    @Getter
    private String description = "global datasource";

    @Getter
    private String user ="user";

    private String password ="";

    private SecretDigest secretDigest = NONE;

    @Getter
    private String url;

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
