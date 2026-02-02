package com.medilabo.solutions.patient.controller;

import com.medilabo.solutions.patient.model.Patient;
import com.medilabo.solutions.patient.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getAll() {
        return patientService.getAll();
    }

    @GetMapping("/{id}")
    public Patient getById(@PathVariable Long id) {
        return patientService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient create(@Valid @RequestBody Patient patient) {
        return patientService.create(patient);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable Long id, @Valid @RequestBody Patient patient) {
        return patientService.update(id, patient);
    }
}