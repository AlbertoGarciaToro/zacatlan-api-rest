package mx.unam.fes.zacatlan.product.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import mx.unam.fes.zacatlan.marketblock.entity.MarketBlockEntity;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 255)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UnitType unitType;

    @Column(nullable = false)
    private boolean approvedByMunicipality;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "market_block_id", nullable = false)
    private MarketBlockEntity marketBlock;
}