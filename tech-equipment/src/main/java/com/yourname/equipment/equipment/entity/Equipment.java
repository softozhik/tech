package com.yourname.equipment.equipment.entity;

import com.yourname.equipment.common.entity.BaseEntity;
import com.yourname.equipment.company.entity.Company;
import com.yourname.equipment.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Сущность техники.
 */
@Entity
@Table(name = "equipment", indexes = {
    @Index(name = "idx_equipment_company_id", columnList = "company_id"),
    @Index(name = "idx_equipment_category_id", columnList = "category_id"),
    @Index(name = "idx_equipment_current_user_id", columnList = "current_user_id"),
    @Index(name = "idx_equipment_status", columnList = "status"),
    @Index(name = "idx_equipment_serial_number", columnList = "serial_number")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, foreignKey = @ForeignKey(name = "fk_equipment_company"))
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(name = "fk_equipment_category"))
    private EquipmentCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_user_id", foreignKey = @ForeignKey(name = "fk_equipment_current_user"))
    private User currentUser;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;

    @Column(name = "inventory_number")
    private String inventoryNumber;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model", nullable = false)
    private String model;

    @Lob
    @Column(name = "specifications")
    private String specifications;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_price", precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "warranty_until")
    private LocalDate warrantyUntil;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    @Builder.Default
    private EquipmentStatus status = EquipmentStatus.AVAILABLE;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_status", nullable = false, length = 50)
    @Builder.Default
    private ConditionStatus conditionStatus = ConditionStatus.GOOD;

    @Lob
    @Column(name = "notes")
    private String notes;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EquipmentAssignment> assignments = new ArrayList<>();

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EquipmentRepair> repairs = new ArrayList<>();

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<EquipmentUpgrade> upgrades = new ArrayList<>();

    /**
     * Статусы техники.
     */
    public enum EquipmentStatus {
        AVAILABLE, IN_USE, IN_REPAIR, RETIRED, RESERVED
    }

    /**
     * Состояние техники.
     */
    public enum ConditionStatus {
        EXCELLENT, GOOD, FAIR, POOR, BROKEN
    }
}
