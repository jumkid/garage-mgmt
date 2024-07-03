package com.jumkid.garage.service.mapper;

import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.model.GarageProfileEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GarageLocationMapper.class})
public interface GarageProfileMapper {

    @BeforeMapping
    default void setContext(GarageProfileEntity entity, @Context MapperContext ctx) {
        ctx.setGarageProfileEntity(entity);
    }

    @BeforeMapping
    default void setContext(GarageProfile dto, @MappingTarget GarageProfileEntity entity, @Context MapperContext ctx) {
        ctx.setGarageProfileEntity(entity);
    }

    @Mapping(target = "garageLocations", source = "entity.garageLocationEntityList")
    GarageProfile entityToDTO(GarageProfileEntity entity, @Context MapperContext ctx);

    @Mapping(target = "garageLocationEntityList", source = "dto.garageLocations")
    GarageProfileEntity dtoToEntity(GarageProfile dto, @MappingTarget GarageProfileEntity entity, @Context MapperContext ctx);
}
