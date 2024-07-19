package com.jumkid.garage.config;

import com.jumkid.garage.service.dto.GarageProfile;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@ConfigurationProperties(prefix = "test-data")
@TestPropertySource(value = "classpath:application.yml")
@Getter @Setter
public class TestObjectsProperties {

    String name;

    String clientId;

    String version;

    GarageProfile garageProfile;

    List<GarageProfile> partialGarageProfiles;

}
