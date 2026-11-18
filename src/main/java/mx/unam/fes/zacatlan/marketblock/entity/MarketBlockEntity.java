package mx.unam.fes.zacatlan.marketblock.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "market_blocks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketBlockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 255)
    private String description;
}