package com.medilabo.solutions.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssessmentDTO {
    private Long patId;
    private String patient;
    private String riskLevel;
}
