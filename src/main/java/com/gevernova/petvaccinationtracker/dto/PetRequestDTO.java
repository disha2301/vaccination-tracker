package com.gevernova.petvaccinationtracker.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetRequestDTO {

    @NotBlank(message = "Pet name is required")
    private String petName;

    @NotBlank(message = "Species is required")
    private String species;

    private String breed;

    @NotBlank(message = "Owner name is required")
    private String ownerName;

    @NotBlank(message = "Owner contact is required")
    @Pattern(regexp = "\\+?\\d{10,15}", message = "Enter a valid phone number")
    private String ownerContact;

    @NotNull(message = "Vaccination list cannot be null")
    private List<VaccinationDTO> vaccinationList;

}
