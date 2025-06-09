package com.gevernova.petvaccinationtracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vaccinations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String vaccineName;

    @Column(nullable = false)
    private LocalDate vaccineDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;
}
