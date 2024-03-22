package com.jumkid.garage.service.mapper;

import com.jumkid.garage.model.GarageLocationEntity;
import com.jumkid.garage.service.dto.GarageLocation;
import org.locationtech.jts.geom.Geometry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.geo.Point;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GarageServiceTimeMapper.class})
public interface GarageLocationMapper {
    @Mapping(target = "garageProfileId", source = "entity.garageProfileEntity.id")
    @Mapping(target = "modificationDate", source = "entity.modifiedOn")
    @Mapping(target = "point", source = "entity.geom", qualifiedBy = GeomToPointMapper.class)
    GarageLocation entityToDTO(GarageLocationEntity entity);
    @Mapping(target = "modifiedOn", source = "dto.modificationDate")
    GarageLocationEntity dtoToEntity(GarageLocation dto);

    @GeomToPointMapper
    default Point geomToPoint(Geometry geom) {
        double x = geom.getCoordinate().getX();
        double y = geom.getCoordinate().getY();
        return new Point(x, y);
    }
}
