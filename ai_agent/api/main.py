

from fastapi import FastAPI, HTTPException
from typing import Dict, Any

from ai_agent.core.data_models import RiskAssessmentRequest, RiskAssessmentResponse
from ai_agent.core.risk_classifier import classify_ai_risk 

app = FastAPI(
    title="AI Act Core Risk Classifier",
    description="Microservice to calculate AI risk classification based on EU AI Act Annexes and Articles.",
    version="1.0.0"
)

@app.post(
    "/api/v1/classify", 
    response_model=RiskAssessmentResponse,
    summary="Submits model metadata for AI Act risk assessment."
)
async def classify_risk_endpoint(request: RiskAssessmentRequest) -> Dict[str, Any]:
    """
    Receives model metadata and returns the full GRC/ESG risk classification.
    """
    try:
        metadata = request.model_dump() 

        result = classify_ai_risk(metadata)

        return result
        
    except Exception as e:
        print(f"Error during risk classification: {e}")
        raise HTTPException(
            status_code=500, 
            detail=f"Internal Server Error during classification logic: {str(e)}"
        )

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=5000)