package com.jumkid.garage.model;

import com.jumkid.share.model.GenericEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Table(name = "garage_profile")
@Entity
@Cacheable
@org.hibernate.annotations.Cache(region = "garageProfile", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@SuperBuilder @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = true)
public class GarageProfileEntity extends GenericEntity{

    @Id
    @Column(name = "garage_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "garage_id_seq")
    @SequenceGenerator(name = "garage_id_seq", sequenceName = "garage_profile_garage_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "description")
    private String description;

    @Column(name = "website")
    private String website;

    @OneToMany(mappedBy = "garageProfileEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.Cache(region="garageLocationList", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private List<GarageLocationEntity> garageLocationEntityList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private List<MechanicProfileEntity> mechanicProfileEntityList;
}
