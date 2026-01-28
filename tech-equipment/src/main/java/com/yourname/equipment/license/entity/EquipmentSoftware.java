package com.yourname.equipment.license.entity;

import com.yourname.equipment.common.entity.BaseEntity;
import com.yourname.equipment.equipment.entity.Equipment;
import com.yourname.equipment.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Сущность привязки лицензий к технике.
 */
@Entity
@Table(name = "equipment_software", indexes = {
    @Index(name = "idx_equipment_software_equipment_id", columnList = "equipment_id"),
    @Index(name = "idx_equipment_software_license_id", columnList = "license_id")
}, uniqueConstraints = {
    @UniqueConstraint(name = "uk_equipment_software", columnNames = {"equipment_id", "license_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentSoftware extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_equipment_software_equipment"))
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_id", nullable = false, foreignKey = @ForeignKey(name = "fk_equipment_software_license"))
    private SoftwareLicense license;

    @Column(name = "installed_at", nullable = false)
    private LocalDateTime installedAt;

    @Column(name = "uninstalled_at")
    private LocalDateTime uninstalledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "installed_by_user_id", foreignKey = @ForeignKey(name = "fk_equipment_software_installed_by"))
    private User installedByUser;

    @Lob
    @Column(name = "notes")
    private String notes;
}
