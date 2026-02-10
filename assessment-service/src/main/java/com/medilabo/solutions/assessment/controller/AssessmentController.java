package com.medilabo.solutions.assessment.controller;

import com.medilabo.solutions.assessment.dto.AssessmentDTO;
import com.medilabo.solutions.assessment.service.AssessmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/{patId}")
    public AssessmentDTO assess(@PathVariable Long patId)
    {
        return assessmentService.assess(patId);
    }
}
