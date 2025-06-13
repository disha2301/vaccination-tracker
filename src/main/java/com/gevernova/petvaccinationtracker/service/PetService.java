package com.gevernova.petvaccinationtracker.service;

import com.gevernova.petvaccinationtracker.dto.PetRequestDTO;
import com.gevernova.petvaccinationtracker.dto.PetResponseDTO;
import com.gevernova.petvaccinationtracker.entity.Pet;
import com.gevernova.petvaccinationtracker.entity.Vaccination;
import com.gevernova.petvaccinationtracker.exceptionhandler.ResourceNotFoundException;
import com.gevernova.petvaccinationtracker.jms.EmailProducer;
import com.gevernova.petvaccinationtracker.mapper.PetMapper;
import com.gevernova.petvaccinationtracker.repository.PetRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PetService implements PetServiceInterface {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final EmailProducer emailProducer;

    public PetResponseDTO createPet(PetRequestDTO dto) {
        Pet pet = petMapper.toEntity(dto);
        Pet saved = petRepository.save(pet);

        if (saved.getOwnerEmail() != null && !saved.getOwnerEmail().isEmpty()) {
            try {
                emailProducer.sendEmail(
                        saved.getOwnerEmail(),
                        "Vaccination Tracker - Pet Registered",
                        "Hi " + saved.getOwnerName() + ", your pet " + saved.getPetName() + " has been successfully registered."
                );
                log.info("Registration email sent to {}", saved.getOwnerEmail());
            } catch (MessagingException e) {
                log.error("Failed to send registration email to {}", saved.getOwnerEmail(), e);
            }
        }
        return petMapper.toDTO(saved);
    }

    public PetResponseDTO getPetById(Long id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id " + id));
        return petMapper.toDTO(pet);
    }

    public List<PetResponseDTO> getAllPets() {
        return petRepository.findAll().stream()
                .map(petMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PetResponseDTO updatePet(Long id, PetRequestDTO dto) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found with id " + id));

        // updates the fields
        pet.setPetName(dto.getPetName());
        pet.setSpecies(dto.getSpecies());
        pet.setBreed(dto.getBreed());
        pet.setOwnerName(dto.getOwnerName());
        pet.setOwnerContact(dto.getOwnerContact());

        // Update vaccinations
        pet.getVaccinationList().clear();
        dto.getVaccinationList().forEach(vdto -> {
            Vaccination vaccination = Vaccination.builder()
                    .vaccineName(vdto.getVaccine_name())
                    .vaccineDate(vdto.getVaccine_date())
                    .pet(pet)
                    .build();
            pet.getVaccinationList().add(vaccination);
        });

        Pet updated = petRepository.save(pet);
        return petMapper.toDTO(updated);
    }

    public void deletePet(Long id) {
        if (!petRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pet not found with id " + id);
        }
        petRepository.deleteById(id);
    }

    public List<PetResponseDTO> getPetsByVaccinationName(String name) {
        return petRepository.findPetsByVaccineName(name).stream()
                .map(petMapper::toDTO)
                .collect(Collectors.toList());
    }
}
