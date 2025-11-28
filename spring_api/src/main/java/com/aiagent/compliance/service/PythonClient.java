// spring_api/src/main/java/com/aiagent/compliance/service/PythonClient.java

package com.aiagent.compliance.service;

import com.aiagent.compliance.model.RiskAssessmentRequest;
import com.aiagent.compliance.model.RiskAssessmentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.util.List; // Import necessário para o fallback

/**
 * WebClient implementation to communicate with the Python Core AI Risk Classifier Microservice.
 * Uses reactive programming (Mono) for non-blocking I/O.
 */
@Service
public class PythonClient {

    @Value("${python.core.url}")
    private String pythonCoreUrl;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        // Inicializa o WebClient com a URL base do Python Core
        this.webClient = WebClient.builder()
                .baseUrl(pythonCoreUrl)
                .build();
    }

    /**
     * Sends the model metadata to the Python Core for risk classification.
     * @param request The metadata DTO.
     * @return Mono<RiskAssessmentResponse> a reactive stream holding the classification result.
     */
    public Mono<RiskAssessmentResponse> classifyRisk(RiskAssessmentRequest request) {
        // Endpoint do Python Core que expõe o risk_classifier.py
        String endpoint = "/api/v1/classify"; 
        
        return webClient.post()
                .uri(endpoint)
                .body(Mono.just(request), RiskAssessmentRequest.class)
                .retrieve()
                .bodyToMono(RiskAssessmentResponse.class)
                // Adiciona um fallback robusto em caso de erro de comunicação com o Python
                .onErrorResume(e -> {
                    System.err.println("Error calling Python Core at " + pythonCoreUrl + ": " + e.getMessage());
                    // Retorna uma resposta de risco mínimo/erro, útil para não quebrar a cadeia reativa
                    RiskAssessmentResponse fallback = new RiskAssessmentResponse();
                    fallback.setRiskCategory("Minimal (System Error)");
                    fallback.setJustification("Classification failed due to communication error with Python Core API. Check Python service availability.");
                    fallback.setAiActArticlesTriggered(List.of("Error: Service Down"));
                    fallback.setEsgRisksActivated(List.of("Error: Service Down"));
                    return Mono.just(fallback);
                });
    }
}