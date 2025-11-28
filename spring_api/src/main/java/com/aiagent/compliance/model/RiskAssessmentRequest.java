
package com.aiagent.compliance.model;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for the request sent to the Python Core API.
 * Maps the metadata defined in the API Specification.
 */
@Data
public class RiskAssessmentRequest {
    private String modelName;
    private String domainApplication;
    private Boolean safetyFunction;
    private Boolean citizenDecisionImpact;
    private Boolean specialPersonalData;
    private Boolean governmentSocialScoring;
    private String dataTraining; // ISO 8601 string
}