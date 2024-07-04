package com.jumkid.garage.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jumkid.share.service.dto.GenericDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.geo.Point;

@JsonIgnoreProperties(ignoreUnknown = true)
@SuperBuilder @Data @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageLocation extends GenericDTO {

    @Min(0L)
    private Long id;

    private Long garageProfileId;

    @NotBlank(message = "address line one is required")
    @Size(max = 255)
    private String addressLineOne;
    @Size(max = 255)
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

    @Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$",
            message = "Invalid phone number. It must contains only digits, +, -, (, ), dot and space.")
    private String phoneNumber;

    @Pattern( regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$",
            message = "Invalid phone number. It must contains only digits, +, -, (, ), dot and space." )
    private String fax;

    private Point point;
}
