package mx.unam.fes.zacatlan.marketblock.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import mx.unam.fes.zacatlan.common.pagination.PageResponse;
import mx.unam.fes.zacatlan.common.pagination.PageableFactory;
import mx.unam.fes.zacatlan.marketblock.dto.MarketBlockRequestDTO;
import mx.unam.fes.zacatlan.marketblock.dto.MarketBlockResponseDTO;
import mx.unam.fes.zacatlan.marketblock.service.MarketBlockService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/market-blocks")
@RequiredArgsConstructor
public class MarketBlockController {

    private static final Set<String> SORT_FIELDS = Set.of("id", "name");

    private final MarketBlockService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarketBlockResponseDTO> create(@Valid @RequestBody MarketBlockRequestDTO request) {
        MarketBlockResponseDTO created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MarketBlockResponseDTO> update(@PathVariable Long id,
                                                         @Valid @RequestBody MarketBlockRequestDTO request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','MERCHANT')")
    public ResponseEntity<MarketBlockResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','MERCHANT')")
    public ResponseEntity<PageResponse<MarketBlockResponseDTO>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String search) {

        var pageable = PageableFactory.of(page, size, sort, SORT_FIELDS,
                Sort.by(Sort.Direction.ASC, "name"));
        return ResponseEntity.ok(PageResponse.from(service.search(search, pageable)));
    }
}