package com.jumkid.garage.controller;

import com.jumkid.garage.exception.GarageProfileDuplicateDisplayNameException;
import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.garage.service.GarageMgmtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/garages")
public class GarageProfileController {

    private final GarageMgmtService garageMgmtService;

    public GarageProfileController(GarageMgmtService garageMgmtService) {
        this.garageMgmtService = garageMgmtService;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GarageProfile get(@PathVariable Long id) throws GarageProfileNotFoundException {
        return garageMgmtService.getGarageProfile(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE')")
    public GarageProfile save(@Valid @RequestBody GarageProfile garageProfile) throws GarageProfileDuplicateDisplayNameException {
        return garageMgmtService.addGarageProfile(garageProfile);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE') && @accessAuthorizer.isOwner(#id)")
    public GarageProfile update(@PathVariable Long id, @RequestBody GarageProfile partialGarageProfile)
            throws GarageProfileNotFoundException, GarageProfileDuplicateDisplayNameException {
        return garageMgmtService.updateGarageProfile(id, partialGarageProfile);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('USER_ROLE', 'ADMIN_ROLE') && @accessAuthorizer.isOwner(#id)")
    public void delete(@PathVariable Long id)
            throws GarageProfileNotFoundException {
        garageMgmtService.deleteGarageProfile(id);
    }
}
