package com.aiagent.compliance.controller;

import com.aiagent.compliance.model.AuditReport;
import com.aiagent.compliance.model.RiskAssessmentRequest;
import com.aiagent.compliance.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/audits")
public class AuditController {

    private final AuditService auditService;

    @Autowired
    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }
   
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AuditReport> submitForAudit(@RequestBody RiskAssessmentRequest request) {
        return auditService.conductFullAudit(request);
    }

    @GetMapping
    public Mono<List<AuditReport>> getAllReports() {
        return auditService.findAllReports();
    }

    @GetMapping("/{id}")
    public Mono<AuditReport> getReportById(@PathVariable Long id) {
        return auditService.findReportById(id);
    }
}