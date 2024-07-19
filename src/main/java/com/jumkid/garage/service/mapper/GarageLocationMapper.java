package com.jumkid.garage.service.mapper;

import com.jumkid.garage.model.GarageLocationEntity;
import com.jumkid.garage.service.dto.GarageLocation;
import com.jumkid.garage.service.dto.GpsCoordinate;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {GarageServiceTimeMapper.class})
public interface GarageLocationMapper {

    @AfterMapping
    default void setParent(@MappingTarget GarageLocationEntity entity, @Context MapperContext ctx) {
        entity.setGarageProfileEntity(ctx.getGarageProfileEntity());
    }

    @Mapping(target = "garageProfileId", source = "entity.garageProfileEntity.id")
    @Mapping(target = "coordinate", source = "entity.geom", qualifiedBy = GeomToCoordinateMapper.class)
    GarageLocation entityToDTO(GarageLocationEntity entity, @Context MapperContext ctx);

    @Mapping(target = "geom", source = "dto.coordinate", qualifiedBy = CoordinateToGeomMapper.class)
    @Mapping(target = "garageProfileEntity.id", source = "dto.garageProfileId")
    GarageLocationEntity dtoToEntity(GarageLocation dto, @MappingTarget GarageLocationEntity entity, @Context MapperContext ctx);

    default GarageLocationEntity dtoToEntity(GarageLocation dto, @Context MapperContext ctx) {
        return dtoToEntity(dto, new GarageLocationEntity(), ctx);
    }

    List<GarageLocationEntity> dtoListToEntityList(List<GarageLocation> garageLocations, @Context MapperContext ctx);

    @GeomToCoordinateMapper
    default GpsCoordinate geomToCoordinate(Geometry geom) {
        double x = geom.getCoordinate().getX();
        double y = geom.getCoordinate().getY();
        return new GpsCoordinate(x, y);
    }

    @CoordinateToGeomMapper
    default Geometry coordinateToGeom(GpsCoordinate gpsCoordinate) {
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(new Coordinate(gpsCoordinate.getX(), gpsCoordinate.getY()));
    }
}
