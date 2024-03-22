package com.jumkid.garage.controller;

import com.jumkid.garage.service.dto.GarageProfile;
import com.jumkid.garage.exception.GarageProfileNotFoundException;
import com.jumkid.garage.service.GarageMgmtService;
import org.springframework.http.HttpStatus;
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
}
