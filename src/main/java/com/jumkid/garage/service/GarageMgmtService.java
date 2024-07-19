package com.jumkid.garage.service;

import com.jumkid.garage.exception.GarageProfileDuplicateDisplayNameException;
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
     * Add new garage profile
     *
     * @param garageProfile
     * @return GarageProfile
     */
    GarageProfile addGarageProfile(GarageProfile garageProfile) throws GarageProfileDuplicateDisplayNameException;

    /**
     * update garage profile
     *
     * @param partialGarageProfile
     * @return
     * @throws GarageProfileDuplicateDisplayNameException
     */
    GarageProfile updateGarageProfile(Long garageProfileId, GarageProfile partialGarageProfile)
            throws GarageProfileNotFoundException, GarageProfileDuplicateDisplayNameException;

    /**
     * delete garage profile
     *
     * @param garageProfileId
     * @throws GarageProfileNotFoundException
     */
    void deleteGarageProfile(Long garageProfileId) throws GarageProfileNotFoundException;
}
