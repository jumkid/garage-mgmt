package com.jumkid.garage.config;

import com.jumkid.garage.config.TestContainerConfig;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(initializers = TestContainerConfig.class)
public @interface EnableTestContainers {
}
