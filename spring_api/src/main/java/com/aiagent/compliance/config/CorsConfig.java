package com.aiagent.compliance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * CORS Configuration for the Spring Boot WebFlux API.
 * This allows the Angular frontend (running on :4200) to communicate
 * with the backend (running on :8080).
 */
@Configuration
public class CorsConfig implements WebFluxConfigurer {

    @Bean
    public WebFluxConfigurer corsConfigurer() {
        return new WebFluxConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") 
                        .allowedOrigins("http://localhost:4200") 
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") 
                        .allowedHeaders("*") 
                        .allowCredentials(true); 
            }
        };
    }
}