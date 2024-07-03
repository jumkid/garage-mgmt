package com.jumkid.garage.repository;

import com.jumkid.garage.model.GarageProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GarageProfileRepository extends JpaRepository<GarageProfileEntity, Long> {

    Optional<GarageProfileEntity> findByDisplayName(String displayName);
}
