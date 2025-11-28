
package com.aiagent.compliance.repository;

import com.aiagent.compliance.model.AuditReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for handling persistence operations for the AuditReport entity.
 */
@Repository
public interface AuditReportRepository extends JpaRepository<AuditReport, Long> {
    // Spring Data JPA automatically provides basic CRUD methods (save, findById, etc.)
}