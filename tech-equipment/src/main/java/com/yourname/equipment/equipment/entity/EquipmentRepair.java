package com.yourname.equipment.equipment.entity;

import com.yourname.equipment.common.entity.BaseEntity;
import com.yourname.equipment.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Сущность истории ремонтов техники.
 */
@Entity
@Table(name = "equipment_repairs", indexes = {
    @Index(name = "idx_repair_equipment_id", columnList = "equipment_id"),
    @Index(name = "idx_repair_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentRepair extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_repair_equipment"))
    private Equipment equipment;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "repair_type", length = 50)
    private String repairType;

    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "vendor")
    private String vendor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    @Builder.Default
    private RepairStatus status = RepairStatus.PENDING;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_by_user_id", foreignKey = @ForeignKey(name = "fk_repair_reported_by"))
    private User reportedByUser;

    @Lob
    @Column(name = "notes")
    private String notes;

    /**
     * Статусы ремонта.
     */
    public enum RepairStatus {
        PENDING, IN_PROGRESS, COMPLETED, CANCELLED
    }
}
