// spring_api/src/main/java/com/aiagent/compliance/model/AuditReport.java

package com.aiagent.compliance.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.Instant;

/**
 * Entity to persist the results of an AI risk audit.
 * This stores the combined output from the Python Core and the Gemini API.
 */
@Entity
@Data 
public class AuditReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Model Metadata from Request
    private String modelName;
    private String domainApplication;
    
    // Core Risk Classification (from Python)
    private String riskCategory; 
    @Column(length = 2048)
    private String justification; 

    // Compliance and ESG Triggers (from Python)
    private String aiActArticlesTriggered; 
    private String esgRisksActivated;

    // AI-Generated Recommendations (from Gemini API)
    @Column(length = 4096)
    private String complianceRecommendations;
    
    // Audit Trail
    private Instant auditTimestamp;
    private String auditorId = "AI_AGENT_V1"; 
}