package com.jumkid.garage.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jumkid.share.service.dto.GenericDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageLocation extends GenericDTO {
    @Min(0L)
    private Long id;
    private Long garageProfileId;
    @NotBlank(message = "address line one is required")
    @Size(max = 255)
    private String addressLineOne;
    private String addressLineTwo;
    @NotBlank(message = "city is required")
    @Size(max = 100)
    private String city;
    @NotBlank(message = "province/state is required")
    @Size(max = 100)
    private String province;
    @NotBlank(message = "country is required")
    @Size(max = 100)
    private String country;

    private String postalCode;

    private String phoneNumber;

    private String fax;

    private Point point;
}
