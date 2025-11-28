package com.aiagent.compliance.service;

import com.aiagent.compliance.model.AuditReport;
import com.aiagent.compliance.model.RiskAssessmentRequest;
import com.aiagent.compliance.model.RiskAssessmentResponse; // <--- ESTE IMPORT É CRUCIAL
import com.aiagent.compliance.repository.AuditReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import reactor.core.scheduler.Schedulers;

/**
 * Orchestration service responsible for the full compliance audit flow
 * and managing the audit trail persistence.
 */
@Service
public class AuditService {

    private final PythonClient pythonClient;
    private final GeminiClient geminiClient;
    private final AuditReportRepository reportRepository;

    @Autowired
    public AuditService(PythonClient pythonClient, GeminiClient geminiClient, AuditReportRepository reportRepository) {
        this.pythonClient = pythonClient;
        this.geminiClient = geminiClient;
        this.reportRepository = reportRepository;
    }

    /**
     * Executes the full compliance audit pipeline reactively.
     */
    public Mono<AuditReport> conductFullAudit(RiskAssessmentRequest request) {
        Mono<RiskAssessmentResponse> riskMono = pythonClient.classifyRisk(request);

        Mono<AuditReport> reportMono = riskMono.flatMap(riskResponse -> {
            
            Mono<String> recommendationsMono = geminiClient.generateRecommendations(
                riskResponse.getRiskCategory(),
                riskResponse.getAiActArticlesTriggered(),
                riskResponse.getEsgRisksActivated()
            );

            return recommendationsMono.map(recommendations -> {
                AuditReport report = new AuditReport();
                report.setModelName(request.getModelName());
                report.setDomainApplication(request.getDomainApplication());
                
                report.setRiskCategory(riskResponse.getRiskCategory());
                report.setJustification(riskResponse.getJustification());
                
                // Converte Lista para String para persistência em JPA
                report.setAiActArticlesTriggered(String.join(";", riskResponse.getAiActArticlesTriggered()));
                report.setEsgRisksActivated(String.join(";", riskResponse.getEsgRisksActivated()));
                
                report.setComplianceRecommendations(recommendations);
                
                report.setAuditTimestamp(Instant.now());
                
                return report;
            });
        })
        .map(reportRepository::save)
        .subscribeOn(Schedulers.boundedElastic());
        
        return reportMono;
    }
    
    /**
     * Retrieves all saved audit reports.
     */
    public Mono<List<AuditReport>> findAllReports() {
        return Mono.fromCallable(reportRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flatMapIterable(list -> list) 
                .collectList(); 
    }
    
    /**
     * Retrieves a single audit report by its ID.
     */
    public Mono<AuditReport> findReportById(Long id) {
        return Mono.fromCallable(() -> reportRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Audit Report not found with ID: " + id)
        ))
        .subscribeOn(Schedulers.boundedElastic());
    }
}