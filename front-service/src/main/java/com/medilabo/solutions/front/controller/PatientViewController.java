package com.medilabo.solutions.front.controller;

import com.medilabo.solutions.front.client.AssessmentClient;
import com.medilabo.solutions.front.client.NoteClient;
import com.medilabo.solutions.front.client.PatientClient;
import com.medilabo.solutions.front.dto.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.medilabo.solutions.front.model.PatientForm;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PatientViewController {

    private final PatientClient patientClient;
    private final NoteClient noteClient;
    private final AssessmentClient assessmentClient;

    public PatientViewController(PatientClient patientClient, NoteClient noteClient, AssessmentClient assessmentClient) {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
        this.assessmentClient = assessmentClient;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/patients";
    }

    @GetMapping("/patients")
    public String listPatients(Model model) {
        model.addAttribute("patients", patientClient.getAllPatients());
        return "patients";
    }

    @GetMapping("/patients/{id}")
    public String patientDetail(@PathVariable Long id, Model model) {
        PatientDTO patient = patientClient.getPatientById(id);
        List<NoteDTO> notes = noteClient.getNotesByPatient(id);
        AssessmentDTO assessment = assessmentClient.getAssessment(id);

        model.addAttribute("patient", patient);
        model.addAttribute("notes", notes);
        model.addAttribute("noteForm", new NoteForm());
        model.addAttribute("riskLevel", assessment.getRiskLevel());

        return "patient-detail";
    }

    @PostMapping("/patients/{id}/notes")
    public String addNote(@PathVariable Long id,
                          @Valid @ModelAttribute("noteForm") NoteForm form,
                          BindingResult binding,
                          RedirectAttributes redirectAttributes) {

        if (binding.hasErrors()) {
            redirectAttributes.addFlashAttribute("noteError", "La note ne peut pas être vide.");
            return "redirect:/patients/" + id;
        }

        PatientDTO patient = patientClient.getPatientById(id);
        String patientLabel = patient.getLastName();

        NoteCreateDTO noteCreateDTO = new NoteCreateDTO(id, patientLabel, form.getNote());
        noteClient.createNote(noteCreateDTO);
        return "redirect:/patients/" + id;
    }

    @PostMapping("/patients/{id}/notes/{noteId}/delete")
    public String deleteNote(@PathVariable Long id, @PathVariable String noteId) {
        noteClient.deleteNote(noteId);
        return "redirect:/patients/" + id;
    }

    @GetMapping("/patients/new")
    public String newPatientForm(Model model) {
        model.addAttribute("mode", "create");
        model.addAttribute("patientForm", new PatientForm());
        return "patient-form";
    }

    @PostMapping("/patients/new")
    public String createPatient(@ModelAttribute PatientForm patientForm) {
        patientClient.createPatient(patientForm);
        return "redirect:/patients";
    }

    @GetMapping("/patients/{id}/edit")
    public String editPatientForm(@PathVariable Long id, Model model) {
        PatientDTO patientDTO = patientClient.getPatientById(id);

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
        patientClient.updatePatient(id, patientForm);
        return "redirect:/patients/" + id;
    }

}