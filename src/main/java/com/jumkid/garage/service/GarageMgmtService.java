package com.jumkid.garage.service;

import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.exception.GarageProfileNotFoundException;

public interface GarageMgmtService {
    /**
     * Fetch existing garage profile by given identifier
     *
     * @param garageId
     * @return GarageProfile
     * @throws GarageProfileNotFoundException
     */
    GarageProfile getGarageProfile(Long garageId) throws GarageProfileNotFoundException;

    /**
     * Save new garage profile
     *
     * @param garageProfile
     * @return GarageProfile
     */
    GarageProfile saveGarageProfile(GarageProfile garageProfile);
}
