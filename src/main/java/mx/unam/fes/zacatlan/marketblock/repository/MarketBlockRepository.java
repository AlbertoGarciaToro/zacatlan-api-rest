package mx.unam.fes.zacatlan.marketblock.repository;

import java.util.Optional;
import mx.unam.fes.zacatlan.marketblock.entity.MarketBlockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface MarketBlockRepository extends Repository<MarketBlockEntity, Long> {

    MarketBlockEntity save(MarketBlockEntity entity);

    Optional<MarketBlockEntity> findById(Long id);

    Page<MarketBlockEntity> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
}