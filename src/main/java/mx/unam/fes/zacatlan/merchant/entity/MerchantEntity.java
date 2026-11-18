package mx.unam.fes.zacatlan.merchant.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import mx.unam.fes.zacatlan.marketblock.entity.MarketBlockEntity;
import mx.unam.fes.zacatlan.user.entity.UserEntity;

@Entity
@Table(name = "merchants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MerchantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String businessName;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "market_block_id", nullable = false)
    private MarketBlockEntity marketBlock;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    private LocalDateTime deactivationDate;
}