package com.medilabo.solutions.front.service;

import com.medilabo.solutions.front.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.medilabo.solutions.front.model.PatientForm;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Service
public class GatewayPatientClient {

    private final RestClient restClient;
    private final String authHeaderValue;

    public GatewayPatientClient(
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

    public List<PatientDTO> getAllPatients() {
        return restClient.get()
                .uri("/patients")
                .header(HttpHeaders.AUTHORIZATION, authHeaderValue)
                .retrieve()
                .body(new ParameterizedTypeReference<List<PatientDTO>>() {});
    }

    public PatientDTO getPatientById(Long id) {
        return restClient.get()
                .uri("/patients/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, authHeaderValue)
                .retrieve()
                .body(PatientDTO.class);
    }

    public PatientDTO createPatient(PatientForm form) {
        return restClient.post()
                .uri("/patients")
                .header(HttpHeaders.AUTHORIZATION, authHeaderValue)
                .body(form)
                .retrieve()
                .body(PatientDTO.class);
    }

    public PatientDTO updatePatient(Long id, PatientForm form) {
        return restClient.put()
                .uri("/patients/{id}", id)
                .header(HttpHeaders.AUTHORIZATION, authHeaderValue)
                .body(form)
                .retrieve()
                .body(PatientDTO.class);
    }

}
