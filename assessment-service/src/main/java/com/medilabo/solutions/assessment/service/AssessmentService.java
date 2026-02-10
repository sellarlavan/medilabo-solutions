package com.medilabo.solutions.assessment.service;

import com.medilabo.solutions.assessment.client.NoteClient;
import com.medilabo.solutions.assessment.client.PatientClient;
import com.medilabo.solutions.assessment.dto.AssessmentDTO;
import com.medilabo.solutions.assessment.dto.NoteDTO;
import com.medilabo.solutions.assessment.dto.PatientDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class AssessmentService {

    private final PatientClient patientClient;
    private final NoteClient noteClient;

    private static final List<String> TRIGGERS = List.of(
            "Hémoglobine A1C",
            "Microalbumine",
            "Taille",
            "Poids",
            "Fumeur",
            "Fumeuse",
            "Anormal",
            "Cholestérol",
            "Vertiges",
            "Rechute",
            "Réaction",
            "Anticorps"
    );

    private static final int FLAGS = Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE;

    private static final List<Pattern> TRIGGER_PATTERNS = List.of(
            Pattern.compile("\\bh[ée]moglobine\\s*a1c\\b", FLAGS),
            Pattern.compile("\\bmicroalbumine\\b", FLAGS),
            Pattern.compile("\\btaille\\b", FLAGS),
            Pattern.compile("\\bpoids\\b", FLAGS),
            Pattern.compile("\\bfum(eur|euse|er)\\b", FLAGS),
            Pattern.compile("\\banormal(e|es|aux)?\\b", FLAGS),
            Pattern.compile("\\bcholest[ée]rol\\b", FLAGS),
            Pattern.compile("\\bvertige(s)?\\b", FLAGS),
            Pattern.compile("\\brechute(s)?\\b", FLAGS),
            Pattern.compile("\\br[ée]action(s)?\\b", FLAGS),
            Pattern.compile("\\banticorps\\b", FLAGS)
    );

    public AssessmentService(PatientClient patientClient, NoteClient noteClient) {
        this.patientClient = patientClient;
        this.noteClient = noteClient;
    }

    public AssessmentDTO assess(Long patId) {
        PatientDTO patient = patientClient.getPatientById(patId);
        List<NoteDTO> notes = noteClient.getNotesByPatient(patId);

        int age = findAge(patient.getBirthDate());
        int triggerCount = countTriggers(notes);

        String riskLevel = findRiskLevel(age, patient.getGender(), triggerCount);

        return new AssessmentDTO(
                patId,
                (patient.getFirstName() + " " + patient.getLastName()).trim(),
                riskLevel
        );
    }

    private int findAge(LocalDate birthDate) {
        if (birthDate == null) return 0;
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private int countTriggers(List<NoteDTO> notes) {
        if (notes == null || notes.isEmpty()) return 0;

        Set<Integer> triggers = new HashSet<>();

        for (NoteDTO n : notes) {
            String noteText = n.getNote();
            if (noteText == null || noteText.isBlank()) continue;

            for (int i = 0; i < TRIGGER_PATTERNS.size(); i++) {
                if (TRIGGER_PATTERNS.get(i).matcher(noteText).find()) {
                    triggers.add(i);
                }
            }
        }
        return triggers.size();
    }



    private String findRiskLevel(int age, String gender, int triggerCount) {
        boolean ageOver30 = age > 30;
        boolean ageUnder30 = age < 30;

        String g = (gender == null) ? "" : gender.trim().toLowerCase();
        boolean male = g.startsWith("m");
        boolean female = g.startsWith("f");

        if (triggerCount < 2) return "None";

        if (ageOver30 && triggerCount >= 2 && triggerCount <= 5) return "Borderline";

        if (ageUnder30) {
            if (male && triggerCount >= 3 && triggerCount <= 4) return "In Danger";
            if (female && triggerCount >= 4 && triggerCount <= 6) return "In Danger";
            if (male && triggerCount >= 5) return "Early onset";
            if (female && triggerCount >= 7) return "Early onset";
        }

        if (ageOver30) {
            if (triggerCount == 6 || triggerCount == 7) return "In Danger";
            if (triggerCount >= 8) return "Early onset";
        }

        return "None";
    }

}
