package com.gevernova.petvaccinationtracker.service;

import com.gevernova.petvaccinationtracker.dto.PetRequestDTO;
import com.gevernova.petvaccinationtracker.dto.PetResponseDTO;
import com.gevernova.petvaccinationtracker.entity.Pet;
import com.gevernova.petvaccinationtracker.entity.Vaccination;
import com.gevernova.petvaccinationtracker.exceptionhandler.ResourceNotFoundException;
import com.gevernova.petvaccinationtracker.repository.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PetService {

    private final PetRepository petRepository;

    public PetResponseDTO createPet(PetRequestDTO dto) {
        Pet pet = mapToEntity(dto);
        Pet saved = petRepository.save(pet);
        return mapToDTO(saved);
    }

    public PetResponseDTO getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id " + id));
        return mapToDTO(pet);
    }

    public List<PetResponseDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PetResponseDTO updatePet(Long id, PetRequestDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id " + id));

        pet.setPetName(dto.getPetName());
        pet.setSpecies(dto.getSpecies());
        pet.setBreed(dto.getBreed());
        pet.setOwnerName(dto.getOwnerName());
        pet.setOwnerContact(dto.getOwnerContact());

        // Ensure the list is initialized before modifying it
        if (pet.getVaccinationList() == null) {
            pet.setVaccinationList(new ArrayList<>());
        } else {
            pet.getVaccinationList().clear();
        }

        dto.getVaccinationList().forEach(vdto -> {
            Vaccination vaccination = Vaccination.builder()
                    .vaccineName(vdto.getVaccine_name())
                    .vaccineDate(vdto.getVaccine_date())
                    .pet(pet)
                    .build();
            pet.getVaccinationList().add(vaccination);
        });

        Pet updated = petRepository.save(pet);
        return mapToDTO(updated);
    }

    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pet not found with id " + id);
        }
        petRepository.deleteById(id);
    }

    public List<PetResponseDTO> getPetsByVaccinationName(String name) {
        return petRepository.findPetsByVaccineName(name).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private Pet mapToEntity(PetRequestDTO dto) {
        Pet pet = Pet.builder()
                .petName(dto.getPetName())
                .species(dto.getSpecies())
                .breed(dto.getBreed())
                .ownerName(dto.getOwnerName())
                .ownerContact(dto.getOwnerContact())
                .vaccinationList(new ArrayList<>())  // ✅ Initialize the list
                .build();

        dto.getVaccinationList().forEach(vdto -> {
            Vaccination vaccination = Vaccination.builder()
                    .vaccineName(vdto.getVaccine_name())
                    .vaccineDate(vdto.getVaccine_date())
                    .pet(pet)
                    .build();
            pet.getVaccinationList().add(vaccination);
        });

        return pet;
    }

    private PetResponseDTO mapToDTO(Pet pet) {
        return PetResponseDTO.builder()
                .id(pet.getId())
                .petName(pet.getPetName())
                .species(pet.getSpecies())
                .breed(pet.getBreed())
                .ownerName(pet.getOwnerName())
                .ownerContact(pet.getOwnerContact())
                .vaccinationList(
                        pet.getVaccinationList().stream()
                                .map(v -> PetResponseDTO.VaccinationDTO.builder()
                                        .vaccine_name(v.getVaccineName())
                                        .vaccine_date(v.getVaccineDate())
                                        .build())
                                .collect(Collectors.toList())
                )
                .build();
    }
}
