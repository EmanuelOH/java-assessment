package com.irwi.assessment.infrastructure.persistences;


import com.irwi.assessment.domain.entities.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
