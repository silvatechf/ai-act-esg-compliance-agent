export interface AuditReport {
  id: number;
  modelName: string;
  domainApplication: string;
  riskCategory: 'Unacceptable' | 'High' | 'Limited' | 'Minimal';
  justification: string;
  
  aiActArticlesTriggered: string;
  esgRisksActivated: string;

  complianceRecommendations: string;
  auditTimestamp: string; 
  auditorId: string;
}

export interface RiskAssessmentRequest {
  modelName: string;
  domainApplication: string;
  safetyFunction: boolean;
  citizenDecisionImpact: boolean;
  specialPersonalData: boolean;
  governmentSocialScoring: boolean;
  dataTraining: string;
}