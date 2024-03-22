package com.jumkid.garage.model;

import com.jumkid.share.model.GenericEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(name = "mechanic_profile")
@Entity
@SuperBuilder @Data @AllArgsConstructor @NoArgsConstructor @EqualsAndHashCode(of = {"id"}, callSuper = true)
public class MechanicProfileEntity extends GenericEntity {

    @Id
    @Column(name = "mechanic_profile_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private GarageProfileEntity garageProfileEntity;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "specialty")
    private String specialty;
}
