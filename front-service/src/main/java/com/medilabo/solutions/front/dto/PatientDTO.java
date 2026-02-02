package com.medilabo.solutions.front.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String gender;
    private String address;
    private String phone;

}
