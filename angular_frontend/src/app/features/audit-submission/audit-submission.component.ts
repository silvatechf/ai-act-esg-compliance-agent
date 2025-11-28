import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuditService } from '../../core/services/audit.service';
import { RiskAssessmentRequest, AuditReport } from '../../core/models/audit-report.model';

@Component({
  selector: 'app-audit-submission',
  templateUrl: './audit-submission.component.html',
  styleUrls: ['./audit-submission.component.scss']
})
export class AuditSubmissionComponent implements OnInit {
  
  auditForm: FormGroup;
  
  isSubmitting = signal(false);
  lastReport = signal<AuditReport | null>(null);
  submissionError = signal<string | null>(null);

  highRiskTriggers = [
    { key: 'safetyFunction', label: 'Safety-Critical Function?' },
    { key: 'citizenDecisionImpact', label: 'Impacts Fundamental Rights (e.g., Credit, Hiring)?' },
    { key: 'specialPersonalData', label: 'Processes Special Category Personal Data (e.g., Biometrics, Health)?' }
  ];

  constructor(
    private fb: FormBuilder,
    private auditService: AuditService
  ) {
    this.auditForm = this.fb.group({
      modelName: ['', Validators.required],
      domainApplication: ['HR Management', Validators.required], 
      safetyFunction: [false],
      citizenDecisionImpact: [false],
      specialPersonalData: [false],
      governmentSocialScoring: [false],
      dataTraining: [this.getCurrentDateString(), Validators.required]
    });
  }

  ngOnInit(): void {
    this.auditForm.get('citizenDecisionImpact')?.setValue(true);
  }

  getCurrentDateString(): string {
    const now = new Date();
    return now.toISOString().split('.')[0] + 'Z';
  }

  /**
   * Handles the submission of the audit form to the Spring Boot Backend.
   */
  onSubmit(): void {
    this.isSubmitting.set(true);
    this.submissionError.set(null);
    this.lastReport.set(null);

    if (this.auditForm.invalid) {
      this.isSubmitting.set(false);
      this.submissionError.set('Please fill out all required fields.');
      return;
    }

    const request: RiskAssessmentRequest = {
      ...this.auditForm.value,
      dataTraining: this.auditForm.value.dataTraining
    };

    this.auditService.conductAudit(request).subscribe({
      next: (report) => {
        this.lastReport.set(report);
        this.isSubmitting.set(false);
        this.submissionError.set(null);
      },
      error: (err) => {
        console.error('Audit submission error:', err);
        this.submissionError.set('API Gateway Error: Could not connect or invalid data format.');
        this.isSubmitting.set(false);
      }
    });
  }
}