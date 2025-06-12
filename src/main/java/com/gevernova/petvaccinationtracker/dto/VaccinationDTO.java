package com.gevernova.petvaccinationtracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VaccinationDTO {

    @NotBlank(message = "Vaccine name is required")
    private String vaccine_name;

    @NotNull(message = "Vaccine date is required")
    @PastOrPresent(message = "Vaccine date must be in the past or present")
    private LocalDate vaccine_date;
}
