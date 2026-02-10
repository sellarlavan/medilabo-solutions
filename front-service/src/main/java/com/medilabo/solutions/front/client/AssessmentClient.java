package com.medilabo.solutions.front.client;

import com.medilabo.solutions.front.config.FeignAuthConfig;
import com.medilabo.solutions.front.dto.AssessmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "gatewayAssessmentClient",
        url = "${gateway.base-url}",
        configuration = FeignAuthConfig.class
)
public interface AssessmentClient {

    @GetMapping("/assessments/{patId}")
    AssessmentDTO getAssessment(@PathVariable("patId") Long patId);

}
