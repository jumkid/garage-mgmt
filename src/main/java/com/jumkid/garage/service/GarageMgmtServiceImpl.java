package com.jumkid.garage.service;

import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.garage.model.GarageProfileEntity;
import com.jumkid.garage.service.handler.DTOHandler;
import com.jumkid.garage.service.mapper.GarageProfileMapper;
import com.jumkid.garage.repository.GarageProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GarageMgmtServiceImpl implements GarageMgmtService{

    private final GarageProfileRepository garageProfileRepository;

    private final GarageProfileMapper garageProfileMapper;

    private final DTOHandler dtoHandler;

    public GarageMgmtServiceImpl(GarageProfileRepository garageProfileRepository,
                                 GarageProfileMapper garageProfileMapper,
                                 DTOHandler dtoHandler) {
        this.garageProfileRepository = garageProfileRepository;
        this.garageProfileMapper = garageProfileMapper;
        this.dtoHandler = dtoHandler;
    }

    @Override
    public GarageProfile getGarageProfile(Long garageId) throws GarageProfileNotFoundException {
        GarageProfileEntity entity = garageProfileRepository.findById(garageId).orElseThrow(() -> new GarageProfileNotFoundException(garageId));

        return garageProfileMapper.entityToDTO(entity);
    }

    @Override
    public GarageProfile saveGarageProfile(GarageProfile garageProfile) {
        dtoHandler.normalize(null, garageProfile, null);

        return null;
    }
}
