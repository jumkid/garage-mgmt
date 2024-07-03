package com.jumkid.garage.service;

import com.jumkid.garage.exception.GarageProfileDuplicateDisplayNameException;
import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.garage.model.GarageProfileEntity;
import com.jumkid.garage.service.handler.DTOHandler;
import com.jumkid.garage.service.mapper.GarageProfileMapper;
import com.jumkid.garage.repository.GarageProfileRepository;
import com.jumkid.garage.service.mapper.MapperContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GarageMgmtServiceImpl implements GarageMgmtService{

    private final GarageProfileRepository garageProfileRepository;

    private final GarageProfileMapper garageProfileMapper;
    private final MapperContext mapperContext;

    private final DTOHandler dtoHandler;

    public GarageMgmtServiceImpl(GarageProfileRepository garageProfileRepository,
                                 GarageProfileMapper garageProfileMapper,
                                 MapperContext mapperContext, DTOHandler dtoHandler) {
        this.garageProfileRepository = garageProfileRepository;
        this.garageProfileMapper = garageProfileMapper;
        this.mapperContext = mapperContext;
        this.dtoHandler = dtoHandler;
    }

    @Override
    public GarageProfile getGarageProfile(Long garageId) throws GarageProfileNotFoundException {
        GarageProfileEntity entity = garageProfileRepository.findById(garageId).orElseThrow(() -> new GarageProfileNotFoundException(garageId));

        return garageProfileMapper.entityToDTO(entity, mapperContext);
    }

    @Override
    @Transactional
    public GarageProfile addGarageProfile(GarageProfile garageProfile) throws GarageProfileDuplicateDisplayNameException {
        String displayName = garageProfile.getDisplayName();
        if (garageProfileRepository.findByDisplayName(displayName).isPresent()) {
            throw new GarageProfileDuplicateDisplayNameException(displayName);
        }

        dtoHandler.normalize(null, garageProfile, null);
        GarageProfileEntity newEntity = garageProfileMapper.dtoToEntity(garageProfile, new GarageProfileEntity(), mapperContext);

        newEntity = garageProfileRepository.save(newEntity);
        log.info("new garage profile is created. id [{}]", newEntity.getId());

        return garageProfileMapper.entityToDTO(newEntity, mapperContext);
    }
}
