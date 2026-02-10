package com.medilabo.solutions.assessment.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class FeignAuthConfig {

    @Bean
    public RequestInterceptor basicAuthInterceptor(
            @Value("${gateway.username}") String username,
            @Value("${gateway.password}") String password
    ) {
        return template -> {
            String token = username + ":" + password;
            String encoded = Base64.getEncoder().encodeToString(token.getBytes(StandardCharsets.UTF_8));
            template.header("Authorization", "Basic " + encoded);
        };
    }
}