package com.gevernova.petvaccinationtracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String petName;

    @Column(nullable = false)
    private String species;

    private String breed;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String ownerContact;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vaccination> vaccinationList = new ArrayList<>();

    // Helper method to sync bidirectional relationship
    public void addVaccination(Vaccination vaccination) {
        vaccination.setPet(this);
        vaccinationList.add(vaccination);
    }

    public void removeVaccination(Vaccination vaccination) {
        vaccinationList.remove(vaccination);
        vaccination.setPet(null);
    }
}
