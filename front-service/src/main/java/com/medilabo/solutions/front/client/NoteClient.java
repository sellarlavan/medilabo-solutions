package com.medilabo.solutions.front.client;

import com.medilabo.solutions.front.config.FeignAuthConfig;
import com.medilabo.solutions.front.dto.NoteCreateDTO;
import com.medilabo.solutions.front.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "gatewayNoteClient",
        url = "${gateway.base-url}",
        configuration = FeignAuthConfig.class
)
public interface NoteClient {

    @GetMapping("/notes/patient/{patId}")
    List<NoteDTO> getNotesByPatient(@PathVariable("patId") Long patId);

    @PostMapping("/notes")
    NoteDTO createNote(@RequestBody NoteCreateDTO noteCreateDTO);

    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable("id") String id);
}
