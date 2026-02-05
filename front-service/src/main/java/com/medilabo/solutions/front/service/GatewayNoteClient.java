package com.medilabo.solutions.front.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class GatewayNoteClient {

    private final RestClient restClient;
    private final String authHeaderValue;

    public GatewayNoteClient(
            @Value("${gateway.base-url}") String baseUrl,
            @Value("${gateway.username}") String username,
            @Value("${gateway.password}") String password
    ) {
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        String token = username + ":" + password;
        this.authHeaderValue = "Basic " + Base64.getEncoder()
                .encodeToString(token.getBytes(StandardCharsets.UTF_8));
    }

    public List<?> getNotesByPatient(Long patId) {
        return restClient.get()
                .uri("/notes/patient/{patId}", patId)
                .header(HttpHeaders.AUTHORIZATION, authHeaderValue)
                .retrieve()
                .body(List.class);
    }

    public Object createNote(Long patId, String patientLabel, String noteText) {
        var payload = java.util.Map.of(
                "patId", patId,
                "patient", patientLabel,
                "note", noteText
        );

        return restClient.post()
                .uri("/notes")
                .header(HttpHeaders.AUTHORIZATION, authHeaderValue)
                .body(payload)
                .retrieve()
                .body(Object.class);
    }

    public void deleteNote(String noteId) {
        restClient.delete()
                .uri("/notes/{id}", noteId)
                .header(HttpHeaders.AUTHORIZATION, authHeaderValue)
                .retrieve()
                .toBodilessEntity();
    }

}
