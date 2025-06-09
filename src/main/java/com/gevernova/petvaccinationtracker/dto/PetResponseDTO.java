package com.gevernova.petvaccinationtracker.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetResponseDTO {

    private Long id;
    private String petName;
    private String species;
    private String breed;
    private String ownerName;
    private String ownerContact;
    private List<VaccinationDTO> vaccinationList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class VaccinationDTO {
        private String vaccine_name;
        private LocalDate vaccine_date;
    }
}
