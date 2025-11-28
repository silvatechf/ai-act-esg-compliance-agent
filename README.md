🛡️ AI Act & ESG Compliance Agent (v1.0)

Enterprise-Grade AI Governance for the European Market

🎯 Executive Summary & Value Proposition

The AI Compliance Agent is a product-ready application designed to automate mandatory AI risk assessment and compliance gap identification under the EU’s transformative AI Act. It translates complex legal mandates into Actionable MLOps/IT Tasks, providing immediate defense against non-compliance penalties.

✨ Core Value Proposition

This Agent serves as a critical bridge between Regulatory Compliance, Ethical Governance (ESG), and Technical Execution.

Feature

Competitive Level

Strategic Benefit

Quantitative Risk Scoring

Level 3 (Competitive)

Translates subjective risk (HIGH) into objective metrics (Score: 35/100), enabling comparison and progression tracking over time.

Compliance Gap Assessment

Level 3 (Competitive)

Identifies specific Non-Compliance Gaps (e.g., "Missing Art. 14: Human Oversight"), providing auditors with a clear corrective action roadmap.

Intelligent Consulting

Copilot Functionality

Uses the Gemini API to generate professional, context-aware executive summaries and immediate technical recommendations.

Audit Trail & Drill-Down

Production Ready

Persists all audit reports in an H2 Database, guaranteeing historical accountability and drill-down capability (/history route).

🏗️ Enterprise Architecture & Technology Stack

The solution is built on a resilient, decoupled Microservices Architecture, showcasing expertise in integrating best-of-breed enterprise and data science tools.

Component

Technology

Role & Business Impact

Orchestrator

Spring Boot 3 (WebFlux) / Java 17

Performance & Scalability: Central API Gateway managing persistence, security (CORS), and reactive communication between all services.

AI Risk Core

FastAPI / Python 3.10

Regulatory Intelligence: Executes rule-based logic to map use cases against the AI Act's Annex III and Article 5 (Prohibited Practices).

Intelligence Layer

Google Gemini API

Consultative Insights: Generates high-level, context-aware executive recommendations based on classification results.

Frontend

Angular 17+ / Tailwind CSS

Professional UX: Provides a personalized interface for risk submission and visualization of the final Certificate of Conformity.

Agent Logic: The GRC Matrix in Action

The core logic immediately translates model purpose into governance requirements:

Use Case Example

Risk Classification

Mandatory Governance Output

HR Hiring/Screening

HIGH

Requires mandatory Risk Management System (Art. 9) and Bias Testing.

General Purpose Chatbot

LIMITED

Requires mandatory Transparency Mechanism (Art. 52) (User Disclosure).

Social Scoring

UNACCEPTABLE

Prohibited Practice (Art. 5). Immediate decommissioning required.

🚀 Developer Setup & Execution

Prerequisites

Java 17+, Python 3.10+, Node.js/npm.

A Gemini API Key must be set as an environment variable (GEMINI_API_KEY).

Execution Guide

To initiate the complete ecosystem, follow the detailed guide in SETUP_GUIDE.md. The process requires three active terminals:

# Example START commands:
# Terminal 1: python -m uvicorn ai_agent.api.main:app --port 5000
# Terminal 2: ./mvnw spring-boot:run
# Terminal 3: ng serve --open



⭐ Conclusion

This project represents a practical, end-to-end implementation of an AI governance framework, proving highly sought-after expertise in both critical regulatory compliance and modern, high-performance microservices architecture.




