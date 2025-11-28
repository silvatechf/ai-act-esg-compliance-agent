
package com.aiagent.compliance.model;

import lombok.Data;
import java.util.List;

@Data
public class RiskAssessmentResponse {
    private String riskCategory;
    private String justification;
    private List<String> aiActArticlesTriggered;
    private List<String> esgRisksActivated;
    private String timestamp;
}