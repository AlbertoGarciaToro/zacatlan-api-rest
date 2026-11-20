package mx.unam.fes.zacatlan.adminauditlog.service;

import lombok.RequiredArgsConstructor;
import mx.unam.fes.zacatlan.adminauditlog.entity.AdminAuditLogEntity;
import mx.unam.fes.zacatlan.adminauditlog.repository.AdminAuditLogRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
}