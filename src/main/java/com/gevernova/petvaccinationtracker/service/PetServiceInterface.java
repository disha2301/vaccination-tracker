package com.gevernova.petvaccinationtracker.service;

import com.gevernova.petvaccinationtracker.dto.PetRequestDTO;
import com.gevernova.petvaccinationtracker.dto.PetResponseDTO;

import java.util.List;

public interface PetServiceInterface {

    PetResponseDTO createPet(PetRequestDTO dto);

    PetResponseDTO getPetById(Long id);

    List<PetResponseDTO> getAllPets();

    PetResponseDTO updatePet(Long id, PetRequestDTO dto);

    void deletePet(Long id);

    List<PetResponseDTO> getPetsByVaccinationName(String name);
}
