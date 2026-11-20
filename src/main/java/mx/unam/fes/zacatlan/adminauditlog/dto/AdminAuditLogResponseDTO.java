package mx.unam.fes.zacatlan.adminauditlog.dto;

import java.time.LocalDateTime;

public record AdminAuditLogResponseDTO(
        Long id,
        String adminUsername,
        String actionDescription,
        LocalDateTime createdAt
) {}