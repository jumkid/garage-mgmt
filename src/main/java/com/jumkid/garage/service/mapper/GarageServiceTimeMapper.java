package com.jumkid.garage.service.mapper;

import com.jumkid.garage.model.GarageServiceTimeEntity;
import com.jumkid.garage.service.dto.GarageServiceTime;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GarageServiceTimeMapper {
    GarageServiceTime entityToDTO(GarageServiceTimeEntity entity);
    GarageServiceTimeEntity dtoToEntity(GarageServiceTime dto);
}
