package com.gevernova.petvaccinationtracker.mapper;

// All DTO to and from Entity conversions
import com.gevernova.petvaccinationtracker.dto.PetRequestDTO;
import com.gevernova.petvaccinationtracker.dto.PetResponseDTO;
import com.gevernova.petvaccinationtracker.dto.VaccinationDTO;
import com.gevernova.petvaccinationtracker.entity.Pet;
import com.gevernova.petvaccinationtracker.entity.Vaccination;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class PetMapper {

    // Converts a PetRequestDTO into a Pet entity
    public Pet toEntity(PetRequestDTO dto) {
        // Initialize the Pet entity with basic fields
        Pet pet = Pet.builder()
                .petName(dto.getPetName())
                .species(dto.getSpecies())
                .breed(dto.getBreed())
                .ownerName(dto.getOwnerName())
                .ownerContact(dto.getOwnerContact())
                .vaccinationList(new ArrayList<>())
                .build();

        // Map each VaccinationDTO to Vaccination entity and set the relationship
        dto.getVaccinationList().forEach(vdto -> {
            Vaccination vaccination = Vaccination.builder()
                    .vaccineName(vdto.getVaccine_name())
                    .vaccineDate(vdto.getVaccine_date())
                    .pet(pet) // establish bidirectional mapping
                    .build();
            pet.getVaccinationList().add(vaccination);
        });

        return pet;
    }

    // Converts a Pet entity into a PetResponseDTO
    public PetResponseDTO toDTO(Pet pet) {
        return PetResponseDTO.builder()
                .id(pet.getId())
                .petName(pet.getPetName())
                .species(pet.getSpecies())
                .breed(pet.getBreed())
                .ownerName(pet.getOwnerName())
                .ownerContact(pet.getOwnerContact())
                // Convert each Vaccination entity to its corresponding DTO
                .vaccinationList(
                        pet.getVaccinationList().stream()
                                .map(v -> VaccinationDTO.builder()
                                        .vaccine_name(v.getVaccineName())
                                        .vaccine_date(v.getVaccineDate())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}

