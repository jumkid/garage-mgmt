package com.jumkid.garage.service;

import com.jumkid.garage.exception.GarageProfileDuplicateDisplayNameException;
import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.garage.model.GarageProfileEntity;
import com.jumkid.garage.service.handler.DTOHandler;
import com.jumkid.garage.service.mapper.GarageProfileMapper;
import com.jumkid.garage.repository.GarageProfileRepository;
import com.jumkid.garage.service.mapper.MapperContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
        GarageProfileEntity entity = garageProfileRepository.findById(garageId)
                .orElseThrow(() -> new GarageProfileNotFoundException(garageId));
        log.debug("Fetched garage profile [id:{}].", garageId);
        return garageProfileMapper.entityToDTO(entity, mapperContext);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public GarageProfile updateGarageProfile(Long garageProfileId, GarageProfile partialGarageProfile)
            throws GarageProfileNotFoundException, GarageProfileDuplicateDisplayNameException {
        GarageProfileEntity oldEntity = garageProfileRepository.findById(garageProfileId)
                .orElseThrow(() -> new GarageProfileNotFoundException(garageProfileId));

        dtoHandler.normalize(garageProfileId, partialGarageProfile, oldEntity);

        garageProfileMapper.updateEntityFromDto(partialGarageProfile, oldEntity, mapperContext);

        GarageProfileEntity updatedEntity = garageProfileRepository.save(oldEntity);
        log.info("Updated garage profile [id:{}] successfully", garageProfileId);

        return garageProfileMapper.entityToDTO(updatedEntity, mapperContext);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ)
    public void deleteGarageProfile(Long garageProfileId) throws GarageProfileNotFoundException {
        GarageProfileEntity garageProfileEntity = garageProfileRepository.findById(garageProfileId)
                                    .orElseThrow(() -> new GarageProfileNotFoundException(garageProfileId));
        garageProfileRepository.delete(garageProfileEntity);
        log.info("Garage profile [id: {}] is deleted", garageProfileId);
    }
}
