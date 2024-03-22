package com.jumkid.garage.service.mapper;

import com.jumkid.garage.model.GarageServiceTimeEntity;
import com.jumkid.garage.service.dto.GarageServiceTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GarageServiceTimeMapper {
    @Mapping(target = "modificationDate", source = "entity.modifiedOn")
    GarageServiceTime entityToDTO(GarageServiceTimeEntity entity);
    @Mapping(target = "modifiedOn", source = "dto.modificationDate")
    GarageServiceTimeEntity dtoToEntity(GarageServiceTime dto);
}
