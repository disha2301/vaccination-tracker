package com.gevernova.petvaccinationtracker.repository;

import com.gevernova.petvaccinationtracker.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT DISTINCT p FROM Pet p JOIN p.vaccinationList v WHERE v.vaccineName = :vaccineName")
    List<Pet> findPetsByVaccineName(@Param("vaccineName") String vaccineName);
}
