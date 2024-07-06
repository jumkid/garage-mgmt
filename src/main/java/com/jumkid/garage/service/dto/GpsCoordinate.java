package com.jumkid.garage.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GpsCoordinate implements Serializable {

    // latitude
    @NotNull
    private double x;

    // longitude
    @NotNull
    private double y;

}
