package mx.unam.fes.zacatlan.common.pagination;

import java.util.Set;
import mx.unam.fes.zacatlan.common.exception.BusinessRuleException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PageableFactory {

    public static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;

    private PageableFactory() {}

    public static Pageable of(int page, int size, String sort,
                              Set<String> allowedSortFields, Sort defaultSort) {
        if (page < 0) {
            throw new BusinessRuleException("Page index must not be negative");
        }
        if (size < 1) {
            throw new BusinessRuleException("Page size must be at least 1");
        }
        int safeSize = Math.min(size, MAX_PAGE_SIZE);
        return PageRequest.of(page, safeSize, parseSort(sort, allowedSortFields, defaultSort));
    }

    private static Sort parseSort(String sort, Set<String> allowed, Sort defaultSort) {
        if (sort == null || sort.isBlank()) {
            return defaultSort;
        }
        String[] parts = sort.split(",");
        String field = parts[0].trim();
        if (!allowed.contains(field)) {
            throw new BusinessRuleException("Sorting by '" + field + "' is not allowed");
        }
        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length > 1) {
            direction = Sort.Direction.fromOptionalString(parts[1].trim())
                    .orElseThrow(() -> new BusinessRuleException("Sort direction must be 'asc' or 'desc'"));
        }
        return Sort.by(direction, field);
    }
}