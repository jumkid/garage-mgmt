package com.jumkid.garage.model;

import com.jumkid.share.model.GenericEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.locationtech.jts.geom.Geometry;

import java.util.List;

@Table(name = "garage_location")
@Entity
@SuperBuilder @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageLocationEntity extends GenericEntity {

    @Id
    @Column(name = "garage_location_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private GarageProfileEntity garageProfileEntity;

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_location_id")
    private List<GarageServiceTimeEntity> garageServiceTimeEntityList;
}
