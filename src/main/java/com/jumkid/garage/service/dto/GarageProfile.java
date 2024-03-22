package com.jumkid.garage.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jumkid.share.service.dto.GenericDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageProfile extends GenericDTO {

    @Min(0L)
    private Long id;

    @NotBlank(message = "display name is required")
    @Size(max = 255)
    private String displayName;

    private String legalName;

    private String description;

    private String website;

    private List<GarageLocation> garageLocations;

    @Builder
    public GarageProfile(Long id, String displayName, String legalName, String description, String website,
                         List<GarageLocation> garageLocations,
                         String createdBy, LocalDateTime creationDate, String modifiedBy, LocalDateTime modificationDate) {
        super(createdBy, creationDate, modifiedBy, modificationDate);
        this.id = id;
        this.displayName = displayName;
        this.legalName = legalName;
        this.description = description;
        this.website = website;
        this.garageLocations = garageLocations;
    }
}
