package com.jumkid.garage;

import com.jumkid.garage.service.dto.GarageLocation;
import com.jumkid.garage.service.dto.GarageProfile;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class GarageProfileArgumentsProvider implements ArgumentsProvider {

    List<GarageProfile> list;

    public GarageProfileArgumentsProvider() {
        list = List.of(GarageProfile.builder()
                .legalName("ABB Garage Shop")
                .description("a test garage profile")
                .displayName("ABB")
                .website("www.abb.com")
                .garageLocations(List.of(GarageLocation.builder()
                        .province("ON")
                        .addressLineOne("123 Horizon Rd")
                        .addressLineTwo("9")
                        .city("Good Wood")
                        .country("Canada")
                        .postalCode("A4B A6C")
                        .phoneNumber("123-456")
                        .fax("999-000")
                        .point(new Point(10, 10))
                        .build()))
                .build());
    }

    public GarageProfile getSampleGarageProfile() {
        return list.get(0);
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(Arguments.of(list.get(0)));
    }
}
