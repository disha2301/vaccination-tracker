package com.gevernova.petvaccinationtracker.dto;

import lombok.*;
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
}
