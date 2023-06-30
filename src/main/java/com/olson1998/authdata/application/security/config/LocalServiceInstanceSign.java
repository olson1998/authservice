package com.olson1998.authdata.application.security.config;

import com.olson1998.authdata.domain.port.security.stereotype.ServiceInstanceSign;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Slf4j

@Getter
@Component
public class LocalServiceInstanceSign implements ServiceInstanceSign {

    private final String value;

    public LocalServiceInstanceSign(Environment env) {
        var inetAddress = InetAddress.getLoopbackAddress().getHostAddress();
        var port = env.getProperty("server.port", Integer.TYPE);
        var signBuild = new StringBuilder(inetAddress);
        if(port != null){
            signBuild.append(':').append(port);
        }
        this.value = signBuild.toString();
        log.info("Microservice will sign as '{}'", this.value);
    }
}
