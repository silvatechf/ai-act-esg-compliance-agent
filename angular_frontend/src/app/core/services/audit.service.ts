import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuditReport, RiskAssessmentRequest } from '../models/audit-report.model';

@Injectable({
  providedIn: 'root'
})
export class AuditService {
  private apiUrl = 'http://localhost:8080/api/v1/audits'; 

  constructor(private http: HttpClient) { }

  
  conductAudit(request: RiskAssessmentRequest): Observable<AuditReport> {
    console.log('Submitting request to Spring Boot:', request);
    return this.http.post<AuditReport>(this.apiUrl, request);
  }


  getAuditHistory(): Observable<AuditReport[]> {
    return this.http.get<AuditReport[]>(this.apiUrl);
  }

  getReportById(id: number): Observable<AuditReport> {
    return this.http.get<AuditReport>(`${this.apiUrl}/${id}`);
  }
}