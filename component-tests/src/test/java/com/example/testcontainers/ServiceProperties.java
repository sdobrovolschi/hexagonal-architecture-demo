package com.example.testcontainers;

import com.playtika.test.common.properties.CommonContainerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("embedded.service")
@Getter
@Setter
public class ServiceProperties extends CommonContainerProperties {

    private String dockerImage;
    private Integer port = 8080;
    private Integer managementPort = 8081;
}
