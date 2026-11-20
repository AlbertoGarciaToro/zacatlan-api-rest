package mx.unam.fes.zacatlan.adminauditlog.controller;

import java.time.LocalDate;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mx.unam.fes.zacatlan.adminauditlog.dto.AdminAuditLogResponseDTO;
import mx.unam.fes.zacatlan.adminauditlog.service.AdminAuditLogService;
import mx.unam.fes.zacatlan.common.pagination.PageResponse;
import mx.unam.fes.zacatlan.common.pagination.PageableFactory;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/audit-logs")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminAuditLogController {

    private static final Set<String> SORT_FIELDS = Set.of("createdAt", "adminUsername");

    private final AdminAuditLogService service;

    @GetMapping
    public ResponseEntity<PageResponse<AdminAuditLogResponseDTO>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String admin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        var pageable = PageableFactory.of(page, size, sort, SORT_FIELDS,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(PageResponse.from(service.search(admin, startDate, endDate, pageable)));
    }
}