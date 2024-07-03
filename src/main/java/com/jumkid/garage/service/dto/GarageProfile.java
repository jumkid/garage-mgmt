package com.jumkid.garage.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jumkid.share.service.dto.GenericDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder @Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageProfile extends GenericDTO {

    @Min(0L)
    private Long id;

    @NotBlank(message = "display name is required")
    @Size(max = 255)
    private String displayName;

    @Size(max = 255)
    private String legalName;

    private String description;

    @Size(min = 3, max = 255)
    private String website;

    private List<GarageLocation> garageLocations;
}
