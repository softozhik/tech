package com.yourname.equipment.equipment.entity;

import com.yourname.equipment.common.entity.BaseEntity;
import com.yourname.equipment.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Сущность истории апгрейдов техники.
 */
@Entity
@Table(name = "equipment_upgrades", indexes = {
    @Index(name = "idx_upgrade_equipment_id", columnList = "equipment_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentUpgrade extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_upgrade_equipment"))
    private Equipment equipment;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "upgrade_type", length = 50)
    private String upgradeType;

    @Column(name = "component_name")
    private String componentName;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "upgraded_at", nullable = false)
    private LocalDateTime upgradedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by_user_id", foreignKey = @ForeignKey(name = "fk_upgrade_performed_by"))
    private User performedByUser;

    @Lob
    @Column(name = "notes")
    private String notes;
}
