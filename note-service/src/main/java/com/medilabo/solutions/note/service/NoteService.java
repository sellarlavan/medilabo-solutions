package com.medilabo.solutions.note.service;

import com.medilabo.solutions.note.model.Note;
import com.medilabo.solutions.note.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotesByPatient(Long patId) {
        return noteRepository.findByPatIdOrderByCreatedAtDesc(patId);
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public void deleteNote(String id) {
        noteRepository.deleteById(id);
    }
}
