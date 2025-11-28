from typing import Dict, Any, Literal, List
from datetime import datetime

RiskCategory = Literal['Unacceptable', 'High', 'Limited', 'Minimal']

def classify_ai_risk(metadata: Dict[str, Any]) -> Dict[str, Any]:
    """
    Classifies the AI system according to the EU AI Act risk categories and 
    identifies triggered AI Act Articles and ESG risks. (Versão Aprimorada)
    """
    
    response_data: Dict[str, Any] = {
        "riskCategory": 'Minimal',
        "justification": "Initially classified as Minimal Risk.",
        "aiActArticlesTriggered": [],
        "esgRisksActivated": [],
        "timestamp": datetime.now().isoformat() + 'Z'
    }
    triggered_articles = set() 
    # --- 1. UNACCEPTABLE RISK CLASSIFICATION (ARTICLE 5) ---
    # Risco Proibido (Prohibited Practices)
    if metadata.get("governmentSocialScoring") or metadata.get("subliminalManipulation"):
        response_data['riskCategory'] = 'Unacceptable'
        response_data['justification'] = "Unacceptable Risk (Prohibited) - Activated by Art. 5. This system falls under banned practices (e.g., social scoring, harmful manipulation)."
        triggered_articles.add("Art. 5 (Prohibited Practices)")
        response_data['esgRisksActivated'].append("S-EthicalViolations")
        response_data['aiActArticlesTriggered'] = list(triggered_articles)
        return response_data

    # --- 2. HIGH RISK CLASSIFICATION (ARTICLE 6 & ANNEX III) ---
    
    high_risk_domains: List[str] = [
        "Biometrics/ID Management",         
        "Critical Infrastructure",          
        "Education/Vocational Training",    
        "HR Management",                    
        "Financial Services (Credit Assessment)", 
        "Law Enforcement",                  
        "Migration/Border Control",         
    ]
    
    
    is_high_risk_annex_iii = (
        metadata.get("domainApplication") in high_risk_domains and 
        metadata.get("citizenDecisionImpact")
    )
    
    is_safety_critical = metadata.get("safetyFunction")
    
    if is_high_risk_annex_iii or is_safety_critical:
        response_data['riskCategory'] = 'High'
        response_data['justification'] = f"High Risk - Activated by Art. 6 and Annex III. Operates in critical domain ('{metadata.get('domainApplication')}') and impacts fundamental rights or safety."
        
        triggered_articles.update([
            "Art. 6 (High-Risk Definition)", 
            "Annex III (High-Risk Areas)",
            "Art. 9 (Risk Management System)",
            "Art. 10 (Data Governance)"
        ])
        
        if metadata.get("specialPersonalData"):
            response_data['justification'] += " NOTE: Use of Special Personal Data mandates strict XAI and Bias Mitigation requirements (Art. 10/13)."
            triggered_articles.add("Art. 13 (Transparency and Information)")
            response_data['esgRisksActivated'].extend(["S-Discrimination", "G-DataPrivacyRisk"])
        
        response_data['aiActArticlesTriggered'] = list(triggered_articles)
        return response_data

    # --- 3. LIMITED RISK CLASSIFICATION (ARTICLE 52) ---
    
    is_limited_risk_app = (
        metadata.get("domainApplication") == "Chatbot/Virtual Assistant" or
        metadata.get("domainApplication") == "Deepfake/Synthesized Content" 
    )

    if is_limited_risk_app:
        response_data['riskCategory'] = 'Limited'
        response_data['justification'] = "Limited Risk - Activated by Art. 52. Requires specific transparency obligations (e.g., labeling content/informing users of AI interaction)."
        triggered_articles.add("Art. 52 (Transparency Obligations)")
        response_data['aiActArticlesTriggered'] = list(triggered_articles)
        return response_data

    # --- 4. MINIMAL RISK ---
    response_data['esgRisksActivated'].append("G-StandardProcess") 
    return response_data