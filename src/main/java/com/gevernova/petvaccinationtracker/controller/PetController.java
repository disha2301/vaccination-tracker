package com.gevernova.petvaccinationtracker.controller;

import com.gevernova.petvaccinationtracker.dto.PetRequestDTO;
import com.gevernova.petvaccinationtracker.dto.PetResponseDTO;
import com.gevernova.petvaccinationtracker.service.PetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping
    public ResponseEntity<PetResponseDTO> createPet(@Valid @RequestBody PetRequestDTO petRequestDTO) {
        return ResponseEntity.ok(petService.createPet(petRequestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponseDTO> getPetById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.getPetById(id));
    }

    @GetMapping
    public ResponseEntity<List<PetResponseDTO>> getAllPets() {
        return ResponseEntity.ok(petService.getAllPets());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponseDTO> updatePet(@PathVariable Long id,
                                                    @Valid @RequestBody PetRequestDTO petRequestDTO) {
        return ResponseEntity.ok(petService.updatePet(id, petRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/vaccinated/{name}")
    public ResponseEntity<List<PetResponseDTO>> getPetsByVaccineName(@PathVariable String name) {
        return ResponseEntity.ok(petService.getPetsByVaccinationName(name));
    }
}
