package com.jumkid.garage.service.mapper;

import com.jumkid.garage.model.GarageLocationEntity;
import com.jumkid.garage.service.dto.GarageLocation;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.mapstruct.*;
import org.springframework.data.geo.Point;

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
    @Mapping(target = "point", source = "entity.geom", qualifiedBy = GeomToPointMapper.class)
    GarageLocation entityToDTO(GarageLocationEntity entity, @Context MapperContext ctx);

    @Mapping(target = "geom", source = "dto.point", qualifiedBy = PointToGeomMapper.class)
    GarageLocationEntity dtoToEntity(GarageLocation dto, @MappingTarget GarageLocationEntity entity, @Context MapperContext ctx);

    default GarageLocationEntity dtoToEntity(GarageLocation dto, @Context MapperContext ctx) {
        return dtoToEntity(dto, new GarageLocationEntity(), ctx);
    }

    List<GarageLocationEntity> dtoListToEntityList(List<GarageLocation> garageLocations, @Context MapperContext ctx);

    @GeomToPointMapper
    default Point geomToPoint(Geometry geom) {
        double x = geom.getCoordinate().getX();
        double y = geom.getCoordinate().getY();
        return new Point(x, y);
    }

    @PointToGeomMapper
    default Geometry pointToGeom(Point point) {
        GeometryFactory factory = new GeometryFactory();
        return factory.createPoint(new Coordinate(point.getX(), point.getY()));
    }
}
