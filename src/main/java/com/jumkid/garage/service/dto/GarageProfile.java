package com.jumkid.garage.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jumkid.share.service.dto.GenericDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder @Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = false)
public class GarageProfile extends GenericDTO {

    @Min(0L)
    private Long id;

    @NotEmpty(message = "display name is required")
    @Size(min = 2, max = 255)
    private String displayName;

    @Size(max = 255)
    private String legalName;

    private String description;

    @Size(min = 3, max = 255)
    private String website;

    @Valid
    private List<GarageLocation> garageLocations;
}
