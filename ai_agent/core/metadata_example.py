

# Example Model Metadata (e.g., a Credit Scoring or Hiring System)
MODEL_METADATA_EXAMPLE = {
    "modelName": "Credit_Eligibility_v1.0",
    "domainApplication": "Financial Services (Credit Assessment)", # High-Risk Area (Annex III)
    
    # EU AI Act Risk Triggers
    "safetyFunction": False, 
    "citizenDecisionImpact": True, # Does the system significantly affect access to services/opportunities? (High Risk)
    "specialPersonalData": True, # Use of sensitive data (biometric, ethnic origin, health)
    
    # Unacceptable Risk Triggers (Article 5)
    "governmentSocialScoring": False, # Prohibited Social Scoring by public authorities
    "subliminalManipulation": False, # Prohibited manipulative techniques
    
    # Context
    "dataTraining": "2025-10-20T10:00:00Z",
    "responsibleTeam": "Data Governance Office"
}