package com.jumkid.garage.model;

import com.jumkid.share.model.GenericEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

@Table(name = "garage_location")
@Entity
@Cacheable
@org.hibernate.annotations.Cache(region="garageLocation", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuperBuilder @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageLocationEntity extends GenericEntity {

    @Id
    @Column(name = "garage_location_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "garage_location_id_seq")
    @SequenceGenerator(name = "garage_location_id_seq",
            sequenceName = "garage_location_garage_location_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "address_line_1")
    private String addressLineOne;

    @Column(name = "address_line_2")
    private String addressLineTwo;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "fax")
    private String fax;

    @Column(name = "geom", columnDefinition = "Geometry")
    private Geometry geom;

    @ManyToOne
    @JoinColumn(name = "garage_id")
    private GarageProfileEntity garageProfileEntity;

    @OneToMany(mappedBy = "garageLocationEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.Cache(region="garageServiceTimeList", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<GarageServiceTimeEntity> garageServiceTimeEntityList;
}
