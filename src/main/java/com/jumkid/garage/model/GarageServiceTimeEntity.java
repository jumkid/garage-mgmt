package com.jumkid.garage.model;

import com.jumkid.share.model.GenericEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "garage_service_time")
@Entity
@Cacheable
@org.hibernate.annotations.Cache(region="garageServiceTime", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuperBuilder @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageServiceTimeEntity extends GenericEntity {

    @Id
    @Column(name = "garage_service_time_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "garage_service_time_id_seq")
    @SequenceGenerator(name = "garage_service_time_id_seq", sequenceName = "garage_service_time_garage_service_time_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
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
