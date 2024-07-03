package com.jumkid.garage.service.mapper;

import com.jumkid.garage.model.GarageProfileEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class MapperContext {

    @Getter @Setter
    private GarageProfileEntity garageProfileEntity;
}
