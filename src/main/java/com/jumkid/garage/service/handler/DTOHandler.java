package com.jumkid.garage.service.handler;

import com.jumkid.garage.model.GarageLocationEntity;
import com.jumkid.garage.model.GarageProfileEntity;
import com.jumkid.garage.service.dto.GarageLocation;
import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.share.service.dto.GenericDTOHandler;
import com.jumkid.share.user.UserProfileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DTOHandler extends GenericDTOHandler{

    public DTOHandler(UserProfileManager userProfileManager) {
        super(userProfileManager);
    }

    public void normalize(Long id, GarageProfile dto, GarageProfileEntity oldEntity) {
        log.trace("normalize dto, id {} oldEntity {}", id, oldEntity);

        if (id != null) { dto.setId(id); }
        if (id == null && oldEntity == null) { dto.setId(null); }

        super.normalizeOwnership(dto, oldEntity);

        if (dto.getGarageLocations() != null) {
            for (GarageLocation garageLocation : dto.getGarageLocations()) {
                GarageLocationEntity entity = null;
                if (oldEntity != null) {
                    entity = oldEntity.getGarageLocationEntityList().stream()
                            .filter(garageLocationEntity -> garageLocationEntity.getId().equals(garageLocation.getId()))
                            .findAny()
                            .orElse(null);
                }

                super.normalizeOwnership(garageLocation, entity);
            }
        }
    }
}
