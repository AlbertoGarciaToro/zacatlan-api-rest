package mx.unam.fes.zacatlan.adminauditlog.service;

import lombok.RequiredArgsConstructor;
import mx.unam.fes.zacatlan.adminauditlog.dto.AdminAuditLogResponseDTO;
import mx.unam.fes.zacatlan.adminauditlog.entity.AdminAuditLogEntity;
import mx.unam.fes.zacatlan.adminauditlog.repository.AdminAuditLogRepository;
import mx.unam.fes.zacatlan.common.exception.BusinessRuleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminAuditLogService {

    private static final int MAX_DESCRIPTION_LENGTH = 500;

    private final AdminAuditLogRepository repository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void record(String actionDescription) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Audit log requires an authenticated admin");
        }

        String description = actionDescription.length() > MAX_DESCRIPTION_LENGTH
                ? actionDescription.substring(0, MAX_DESCRIPTION_LENGTH)
                : actionDescription;

        repository.save(AdminAuditLogEntity.builder()
                .adminUsername(authentication.getName())
                .actionDescription(description)
                .build());
    }

    @Transactional(readOnly = true)
    public Page<AdminAuditLogResponseDTO> search(String admin, LocalDate startDate,
                                                 LocalDate endDate, Pageable pageable) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new BusinessRuleException("startDate must not be after endDate");
        }
        LocalDateTime from = startDate != null ? startDate.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
        LocalDateTime to = endDate != null ? endDate.plusDays(1).atStartOfDay() : LocalDateTime.of(2999, 1, 1, 0, 0);
        String adminFilter = admin == null ? "" : admin.trim();

        return repository.search(adminFilter, from, to, pageable)
                .map(a -> new AdminAuditLogResponseDTO(
                        a.getId(), a.getAdminUsername(), a.getActionDescription(), a.getCreatedAt()));
    }
}