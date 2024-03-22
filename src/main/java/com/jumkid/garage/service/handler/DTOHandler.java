package com.jumkid.garage.service.handler;

import com.jumkid.garage.model.GarageProfileEntity;
import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.share.user.UserProfileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DTOHandler {
    private final UserProfileManager userProfileManager;

    public DTOHandler(UserProfileManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    public void normalize(Long id, GarageProfile dto, GarageProfileEntity oldEntity) {
        //keep dto id sync with id argument
        if (id != null) { dto.setId(id); }
        if (id == null && oldEntity == null) { dto.setId(null); }
    }
}
