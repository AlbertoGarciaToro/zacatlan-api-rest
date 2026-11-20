package mx.unam.fes.zacatlan.adminauditlog.repository;

import mx.unam.fes.zacatlan.adminauditlog.entity.AdminAuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface AdminAuditLogRepository extends Repository<AdminAuditLogEntity, Long> {

    AdminAuditLogEntity save(AdminAuditLogEntity entity);

    Page<AdminAuditLogEntity> findAll(Pageable pageable);
}