package com.medilabo.solutions.assessment.client;

import com.medilabo.solutions.assessment.config.FeignAuthConfig;
import com.medilabo.solutions.assessment.dto.NoteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "gatewayNoteClient",
        url = "${gateway.base-url}",
        configuration = FeignAuthConfig.class
)
public interface NoteClient {

    @GetMapping("/notes/patient/{patId}")
    List<NoteDTO> getNotesByPatient(@PathVariable("patId") Long patId);
}
