package com.gevernova.petvaccinationtracker.controller;

import com.gevernova.petvaccinationtracker.dto.PetRequestDTO;
import com.gevernova.petvaccinationtracker.dto.PetResponseDTO;
import com.gevernova.petvaccinationtracker.service.PetService;
import com.gevernova.petvaccinationtracker.service.PetServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetServiceInterface petService;

    // http://localhost:8080/api/pet
    @PostMapping
    public ResponseEntity<PetResponseDTO> createPet(@Valid @RequestBody PetRequestDTO petRequestDTO) {
        return ResponseEntity.ok(petService.createPet(petRequestDTO));
    }

    // http://localhost:8080/api/pet/2
    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> getPetById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    // http://localhost:8080/api/pet
    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    // http://localhost:8080/api/pet/2
    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> updatePet(@PathVariable Long id,
                                                    @Valid @RequestBody PetRequestDTO petRequestDTO) {
        return ResponseEntity.ok(petService.updatePet(id, petRequestDTO));
    }

    // http://localhost:8080/api/pet/2
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    // http://localhost:8080/api/pet/vaccinated/Rabies
    @GetMapping("/vaccinated/{name}")
    public ResponseEntity<List<PetResponseDTO>> getPetsByVaccineName(@PathVariable String name) {
        return ResponseEntity.ok(petService.getPetsByVaccinationName(name));
    }
}
