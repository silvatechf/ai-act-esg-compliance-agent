
from pydantic import BaseModel, Field
from typing import List

class RiskAssessmentRequest(BaseModel):
    """
    Data model for the incoming model metadata from the Spring Backend.
    """
    modelName: str
    domainApplication: str = Field(..., description="The sector of application, e.g., 'HR Management'.")
    safetyFunction: bool
    citizenDecisionImpact: bool
    specialPersonalData: bool
    governmentSocialScoring: bool
    dataTraining: str 

# --- DTO for Response (Output to Spring Backend) ---
class RiskAssessmentResponse(BaseModel):
    """
    Data model for the outgoing risk classification report (GRC/ESG matrix).
    """
    riskCategory: str
    justification: str
    aiActArticlesTriggered: List[str]
    esgRisksActivated: List[str]
    timestamp: str