package com.medilabo.solutions.front.controller;

import com.medilabo.solutions.front.dto.PatientDTO;
import com.medilabo.solutions.front.service.GatewayPatientClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.medilabo.solutions.front.model.PatientForm;

import java.time.LocalDate;
import java.util.Map;


@Controller
public class PatientViewController {

    private final GatewayPatientClient client;

    public PatientViewController(GatewayPatientClient client) {
        this.client = client;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", client.getAllPatients());
        return "patients";
    }

    @GetMapping("/patients/{id}")
    public String patientDetail(@PathVariable Long id, Model model) {
        model.addAttribute("patient", client.getPatientById(id));
        return "patient-detail";
    }

    @GetMapping("/patients/new")
    public String newPatientForm(Model model) {
        model.addAttribute("mode", "create");
        model.addAttribute("patientForm", new PatientForm());
        return "patient-form";
    }

    @PostMapping("/patients/new")
    public String createPatient(@ModelAttribute PatientForm patientForm) {
        client.createPatient(patientForm);
        return "redirect:/patients";
    }

    @GetMapping("/patients/{id}/edit")
    public String editPatientForm(@PathVariable Long id, Model model) {
        PatientDTO patientDTO = client.getPatientById(id);

        PatientForm form = new PatientForm();
        form.setFirstName((String) patientDTO.getFirstName());
        form.setLastName((String) patientDTO.getLastName());
        form.setBirthDate(patientDTO.getBirthDate());
        form.setGender((String) patientDTO.getGender());
        form.setAddress((String) patientDTO.getAddress());
        form.setPhone((String) patientDTO.getPhone());



        model.addAttribute("mode", "edit");
        model.addAttribute("patientId", id);
        model.addAttribute("patientForm", form);
        return "patient-form";
    }

    @PostMapping("/patients/{id}/edit")
    public String updatePatient(@PathVariable Long id, @ModelAttribute PatientForm patientForm) {
        client.updatePatient(id, patientForm);
        return "redirect:/patients/" + id;
    }

}
