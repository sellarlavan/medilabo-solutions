package com.medilabo.solutions.front.client;

import com.medilabo.solutions.front.config.FeignAuthConfig;
import com.medilabo.solutions.front.dto.PatientDTO;
import com.medilabo.solutions.front.model.PatientForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(
        name = "gatewayPatientClient",
        url = "${gateway.base-url}",
        configuration = FeignAuthConfig.class
)
public interface PatientClient {

    @GetMapping("/patients")
    List<PatientDTO> getAllPatients();

    @GetMapping("/patients/{id}")
    PatientDTO getPatientById(@PathVariable("id") Long id);

    @PostMapping("/patients")
    PatientDTO createPatient(@RequestBody PatientForm form);

    @PutMapping("/patients/{id}")
    PatientDTO updatePatient(@PathVariable("id") Long id, @RequestBody PatientForm form);
}
