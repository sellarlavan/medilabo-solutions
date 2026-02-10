package com.medilabo.solutions.assessment.client;

import com.medilabo.solutions.assessment.config.FeignAuthConfig;
import com.medilabo.solutions.assessment.dto.PatientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "gatewayPatientClient",
        url = "${gateway.base-url}",
        configuration = FeignAuthConfig.class
)
public interface PatientClient {

    @GetMapping("/patients/{id}")
    PatientDTO getPatientById(@PathVariable("id") Long id);
}
