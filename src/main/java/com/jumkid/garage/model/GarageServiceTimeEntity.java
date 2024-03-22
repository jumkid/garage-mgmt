package com.jumkid.garage.model;

import com.jumkid.share.model.GenericEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "garage_service_time")
@Entity
@SuperBuilder @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageServiceTimeEntity extends GenericEntity {

    @Id
    @Column(name = "garage_service_time_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_location_id")
    private GarageLocationEntity garageLocationEntity;

    @Column(name = "weekday")
    @Enumerated(EnumType.ORDINAL)
    private DayOfWeek weekday;

    @Column(name = "specific_date")
    private LocalDate specificDate;

    @Column(name = "time_from")
    private LocalTime timeFrom;

    @Column(name = "time_to")
    private LocalTime timeTo;

    @Column(name = "available")
    private Boolean available;

}
