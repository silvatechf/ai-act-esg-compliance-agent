package com.aiagent.compliance.service;

import com.aiagent.compliance.model.GeminiRequest;
import com.aiagent.compliance.model.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class GeminiClient {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        // NÃO adicionamos key aqui
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }

    public Mono<String> generateRecommendations(
            String riskCategory,
            List<String> aiActArticlesTriggered,
            List<String> esgRisksActivated
    ) {
        
        String compliancePrompt = String.format(
                "Act as a Senior AI Governance Consultant. The model risk level is %s. " +
                "The following EU AI Act articles were triggered: %s. " +
                "The critical ESG risks identified are: %s. " +
                "Generate a concise, professional summary (max 150 words) describing the next steps.",
                riskCategory,
                String.join(", ", aiActArticlesTriggered),
                String.join(", ", esgRisksActivated)
        );

        GeminiRequest request = GeminiRequest.builder()
                .contents(List.of(
                        GeminiRequest.Content.builder()
                                .parts(List.of(
                                        GeminiRequest.Part.builder()
                                                .text(compliancePrompt)
                                                .build()))
                                .build()))
                .build();

        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("key", apiKey)   // AQUI É O PONTO CRÍTICO
                        .build())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .map(response -> {
                    if (response.getCandidates() != null && !response.getCandidates().isEmpty()) {
                        return response.getCandidates().get(0).getContent().getParts().get(0).getText();
                    }
                    return "Error: Could not retrieve a valid response from Gemini API.";
                })
                .onErrorResume(e -> {
                    System.err.println("GEMINI API ERROR: " + e.getMessage());
                    e.printStackTrace();
                    return Mono.just("Error: Gemini API call failed. Check API Key and network.");
                });
    }
}
