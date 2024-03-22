package com.jumkid.garage.service.mapper;

import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.model.GarageProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GarageLocationMapper.class})
public interface GarageProfileMapper {

    @Mapping(target = "modificationDate", source = "entity.modifiedOn")
    @Mapping(target = "garageLocations", source = "entity.garageLocationEntityList")
    GarageProfile entityToDTO(GarageProfileEntity entity);

    @Mapping(target = "modifiedOn", source = "dto.modificationDate")
    GarageProfileEntity dtoToEntity(GarageProfile dto);
}
