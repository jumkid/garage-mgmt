package com.jumkid.garage.service;

import com.jumkid.garage.model.GarageLocationEntity;
import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.garage.model.GarageProfileEntity;
import com.jumkid.garage.service.mapper.GarageProfileMapper;
import com.jumkid.garage.repository.GarageProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GarageMgmtServiceImpl implements GarageMgmtService{

    private final GarageProfileRepository garageProfileRepository;

    private final GarageProfileMapper garageProfileMapper;

    public GarageMgmtServiceImpl(GarageProfileRepository garageProfileRepository, GarageProfileMapper garageProfileMapper) {
        this.garageProfileRepository = garageProfileRepository;
        this.garageProfileMapper = garageProfileMapper;
    }

    @Override
    public GarageProfile getGarageProfile(Long garageId) throws GarageProfileNotFoundException {
        GarageProfileEntity entity = garageProfileRepository.findById(garageId).orElseThrow(() -> new GarageProfileNotFoundException(garageId));
        List<GarageLocationEntity> garageLocationEntityList = entity.getGarageLocationEntityList();

        return garageProfileMapper.entityToDTO(entity);
    }

    @Override
    public GarageProfile saveGarageProfile(GarageProfile garageProfile) {
        return null;
    }
}
