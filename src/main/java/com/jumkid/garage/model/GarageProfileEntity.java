package com.jumkid.garage.model;

import com.jumkid.share.model.GenericEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Table(name = "garage_profile")
@Entity
@SuperBuilder @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageProfileEntity extends GenericEntity{

    @Id
    @Column(name = "garage_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "description")
    private String description;

    @Column(name = "website")
    private String website;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private List<GarageLocationEntity> garageLocationEntityList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private List<MechanicProfileEntity> mechanicProfileEntityList;
}
