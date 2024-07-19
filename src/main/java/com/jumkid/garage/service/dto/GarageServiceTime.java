package com.jumkid.garage.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jumkid.share.service.dto.GenericDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageServiceTime extends GenericDTO {
    @Min(0L)
    private Long id;

    private DayOfWeek weekday;
    private LocalDate specificDate;
    @NotNull
    private LocalTime timeFrom;
    @NotNull
    private LocalTime timeTo;
    @NotNull
    private boolean available;
}
