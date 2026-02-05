package com.medilabo.solutions.front.dto;

import jakarta.validation.constraints.NotBlank;

public class NoteForm {

    @NotBlank
    private String note;

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
