package mx.unam.fes.zacatlan.marketblock.service;

import lombok.RequiredArgsConstructor;
import mx.unam.fes.zacatlan.adminauditlog.service.AdminAuditLogService;
import mx.unam.fes.zacatlan.common.exception.ConflictException;
import mx.unam.fes.zacatlan.common.exception.ResourceNotFoundException;
import mx.unam.fes.zacatlan.marketblock.dto.MarketBlockRequestDTO;
import mx.unam.fes.zacatlan.marketblock.dto.MarketBlockResponseDTO;
import mx.unam.fes.zacatlan.marketblock.entity.MarketBlockEntity;
import mx.unam.fes.zacatlan.marketblock.repository.MarketBlockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MarketBlockService {

    private final MarketBlockRepository repository;
    private final AdminAuditLogService auditLogService;

    @Transactional
    public MarketBlockResponseDTO create(MarketBlockRequestDTO request) {
        String name = request.name().trim();
        if (repository.existsByNameIgnoreCase(name)) {
            throw new ConflictException("A market block named '" + name + "' already exists");
        }
        MarketBlockEntity saved = repository.save(MarketBlockEntity.builder()
                .name(name)
                .description(request.description())
                .build());
        auditLogService.record("Created market block #" + saved.getId() + " (" + saved.getName() + ")");
        return toResponse(saved);
    }

    @Transactional
    public MarketBlockResponseDTO update(Long id, MarketBlockRequestDTO request) {
        MarketBlockEntity block = findOrThrow(id);
        String name = request.name().trim();
        if (repository.existsByNameIgnoreCaseAndIdNot(name, id)) {
            throw new ConflictException("A market block named '" + name + "' already exists");
        }
        block.setName(name);
        block.setDescription(request.description());
        auditLogService.record("Updated market block #" + id + " (" + name + ")");
        return toResponse(block);
    }

    @Transactional(readOnly = true)
    public MarketBlockResponseDTO getById(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<MarketBlockResponseDTO> search(String search, Pageable pageable) {
        String filter = search == null ? "" : search.trim();
        return repository.findByNameContainingIgnoreCase(filter, pageable).map(this::toResponse);
    }

    private MarketBlockEntity findOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Market block not found: " + id));
    }

    private MarketBlockResponseDTO toResponse(MarketBlockEntity block) {
        return new MarketBlockResponseDTO(block.getId(), block.getName(), block.getDescription());
    }
}