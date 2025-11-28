import { Component, OnInit, signal } from '@angular/core';
import { AuditReport } from '../../core/models/audit-report.model';
import { AuditService } from '../../core/services/audit.service'; // Caminho corrigido

@Component({
  selector: 'app-audit-history',
  templateUrl: './audit-history.component.html',
  styleUrls: ['./audit-history.component.scss']
})
export class AuditHistoryComponent implements OnInit {

  // Signals para gerenciar o estado da lista
  auditReports = signal<AuditReport[]>([]);
  selectedReport = signal<AuditReport | null>(null); // ESTADO CRÍTICO: Relatório a ser exibido no detalhe
  
  isLoading = signal(true);
  historyError = signal<string | null>(null);
  isDetailLoading = signal(false);

  // Tipagem explícita no constructor
  constructor(private auditService: AuditService) { }

  ngOnInit(): void {
    this.loadHistory();
  }

  /**
   * Loads the audit history from the Spring Boot Backend (GET /api/v1/audits).
   */
  loadHistory(): void {
    this.isLoading.set(true);
    this.historyError.set(null);
    this.selectedReport.set(null); // Reseta o detalhe ao carregar a lista
    this.auditReports.set([]);

    this.auditService.getAuditHistory().subscribe({
      // Tipagem explícita para 'reports'
      next: (reports: AuditReport[]) => { 
        // Tipagem explícita para 'a' e 'b'
        reports.sort((a: AuditReport, b: AuditReport) => b.id - a.id); 
        this.auditReports.set(reports);
        this.isLoading.set(false);
      },
      // Tipagem explícita para 'err'
      error: (err: any) => { 
        console.error('Failed to load audit history:', err);
        this.historyError.set('Could not fetch audit records from the backend.');
        this.isLoading.set(false);
      }
    });
  }

  /**
   * Fetches a specific report by ID and sets it as the selectedReport.
   * Acionado pelo botão "View Detail" na tabela.
   */
  viewReportDetail(id: number): void {
    this.isDetailLoading.set(true);
    this.selectedReport.set(null);
    this.historyError.set(null);

    this.auditService.getReportById(id).subscribe({
      // Tipagem explícita para 'report'
      next: (report: AuditReport) => { 
        this.selectedReport.set(report); // Define o relatório para visualização
        this.isDetailLoading.set(false);
      },
      // Tipagem explícita para 'err'
      error: (err: any) => { 
        console.error('Failed to load report detail:', err);
        this.historyError.set(`Failed to load report ID ${id}. Check backend.`);
        this.isDetailLoading.set(false);
      }
    });
  }

  /**
   * Utility to clear the detailed view and return to the list.
   * Acionado pelo botão "Back to Audit List".
   */
  clearDetail(): void {
    this.selectedReport.set(null);
  }
}