Setup and Execution Guide: AI Act & ESG Compliance Agent

This document provides the step-by-step instructions to run the complete microservices architecture for the AI Governance Copilot.

1. Development Prerequisites

Ensure the following tools are installed on your machine:

Java Development Kit (JDK): Version 17 or higher.

Apache Maven: (Used by the ./mvnw wrapper).

Python: Version 3.10 or higher (with pip and venv).

Node.js & npm: (For Angular CLI).

Google Gemini API Key: (Required for the Spring Boot service to communicate with the LLM).

2. Starting the 3 Microservices Concurrently

The full-stack agent requires three active terminal processes. Ensure each terminal is navigated to the correct project root (ai_act_esg_agent/).

2.1. Security Configuration (Environment Variable)

This step is mandatory and allows Spring Boot to securely read your API key. Execute this command in the terminal where you will run Spring Boot:

# Set the environment variable for secure key injection (PowerShell syntax)
$env:GEMINI_API_KEY="YOUR_API_KEY_HERE"


2.2. Terminal 1: AI Risk Core (Python FastAPI)

This service runs the risk classification logic and listens on port 5000.

# 1. Navigate to the Python folder
cd ai_agent
# 2. Activate the virtual environment
.\venv\Scripts\activate
# 3. Install dependencies (First time or after updates)
pip install -r requirements.txt
# 4. Start the FastAPI Server
python -m uvicorn ai_agent.api.main:app --reload --port 5000 --host 0.0.0.0


2.3. Terminal 2: Orchestrator (Spring Boot)

The Java Backend listens on port 8080 and manages all API calls and data persistence.

# 1. Navigate to the Spring Boot folder
cd spring_api
# 2. Ensure the GEMINI_API_KEY is set in this shell!
# 3. Start Spring Boot
./mvnw spring-boot:run


2.4. Terminal 3: Frontend (Angular)

The Web Dashboard listens on port 4200.

# 1. Navigate to the Angular folder
cd angular_frontend
# 2. Install dependencies (First time setup only)
npm install
# 3. Start the Angular Server
ng serve --open


3. End-to-End Testing (Final Validation)

Access your browser: http://localhost:4200/submit.

Submit the form with the High-Risk scenario (Domain HR + Impacts Fundamental Rights).

Verify the Certificate of Compliance appears immediately on the submission page, showing the Compliance Score and Non-Compliance Gaps.

Verify the Audit Trail (/history) shows the history and allows drill-down to the detailed report.