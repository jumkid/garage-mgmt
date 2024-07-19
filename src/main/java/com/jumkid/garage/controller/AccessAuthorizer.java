package com.jumkid.garage.controller;

import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.garage.service.GarageMgmtService;
import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.share.security.exception.UserProfileNotFoundException;
import com.jumkid.share.user.UserProfile;
import com.jumkid.share.user.UserProfileManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccessAuthorizer {

    private final UserProfileManager userProfileManager;

    private final GarageMgmtService garageMgmtService;

    public AccessAuthorizer(UserProfileManager userProfileManager, GarageMgmtService garageMgmtService) {
        this.userProfileManager = userProfileManager;
        this.garageMgmtService = garageMgmtService;
    }

    public boolean isOwner(long garageProfileId) {
        try {
            GarageProfile garageProfile = garageMgmtService.getGarageProfile(garageProfileId);
            return isCurrentUser(garageProfile.getCreatedBy());
        } catch (GarageProfileNotFoundException ex) {
            log.info("Garage profile [id: {}] is not found", garageProfileId);
            return true;
        }
    }

    private boolean isCurrentUser(String userId) {
        return this.getUserProfile().getId().equals(userId);
    }

    private UserProfile getUserProfile() {
        UserProfile userProfile = userProfileManager.fetchUserProfile();
        if (userProfile == null) {
            throw new UserProfileNotFoundException("User profile could not be found. Access is denied");
        } else {
            return userProfile;
        }
    }
}
