package com.medilabo.solutions.patient.service;

import com.medilabo.solutions.patient.exception.PatientNotFoundException;
import com.medilabo.solutions.patient.model.Patient;
import com.medilabo.solutions.patient.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAll() {
        return patientRepository.findAll();
    }

    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
    }

    public Patient create(Patient patient) {
        patient.setId(null);
        return patientRepository.save(patient);
    }

    public Patient update(Long id, Patient updatedPatient) {
        Patient existingPatient = getById(id);

        existingPatient.setFirstName(updatedPatient.getFirstName());
        existingPatient.setLastName(updatedPatient.getLastName());
        existingPatient.setBirthDate(updatedPatient.getBirthDate());
        existingPatient.setGender(updatedPatient.getGender());
        existingPatient.setAddress(updatedPatient.getAddress());
        existingPatient.setPhone(updatedPatient.getPhone());

        return patientRepository.save(existingPatient);
    }
}
