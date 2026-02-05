package com.medilabo.solutions.note.repository;

import com.medilabo.solutions.note.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByPatIdOrderByCreatedAtDesc(Long patId);
}
