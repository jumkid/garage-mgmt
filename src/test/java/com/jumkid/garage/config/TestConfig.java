package com.jumkid.garage.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = TestObjectsProperties.class)
public class TestConfig {
    //void
}
