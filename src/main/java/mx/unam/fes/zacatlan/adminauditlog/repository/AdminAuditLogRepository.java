package mx.unam.fes.zacatlan.adminauditlog.repository;

import mx.unam.fes.zacatlan.adminauditlog.entity.AdminAuditLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AdminAuditLogRepository extends Repository<AdminAuditLogEntity, Long> {

    AdminAuditLogEntity save(AdminAuditLogEntity entity);

    Page<AdminAuditLogEntity> findAll(Pageable pageable);

    @Query("""
        SELECT a FROM AdminAuditLogEntity a
        WHERE LOWER(a.adminUsername) LIKE LOWER(CONCAT('%', :admin, '%'))
          AND a.createdAt >= :from
          AND a.createdAt < :to
        """)
    Page<AdminAuditLogEntity> search(@Param("admin") String admin,
                                     @Param("from") LocalDateTime from,
                                     @Param("to") LocalDateTime to,
                                     Pageable pageable);
}