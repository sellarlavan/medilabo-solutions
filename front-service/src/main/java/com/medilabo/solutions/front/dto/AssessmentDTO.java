package com.medilabo.solutions.front.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentDTO {
    private Long patId;
    private String patient;
    private String riskLevel;
}
