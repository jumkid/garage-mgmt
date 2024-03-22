package com.jumkid.garage.repository;

import com.jumkid.garage.model.GarageLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarageLocationRepository extends JpaRepository<GarageLocationEntity, Long> {

    @Query(value = "SELECT * FROM garage_location WHERE ST_DWithin(geom, 'POINT(':x' ':y')', :radius)", nativeQuery = true)
    List<GarageLocationEntity> findAllByGeomWithRadius(@Param("x") double x,
                                                       @Param("y") double y,
                                                       @Param("radius") double radius);
}
