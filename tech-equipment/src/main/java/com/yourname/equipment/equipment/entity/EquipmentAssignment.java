package com.yourname.equipment.equipment.entity;

import com.yourname.equipment.common.entity.BaseEntity;
import com.yourname.equipment.department.entity.Department;
import com.yourname.equipment.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Сущность истории назначений техники.
 */
@Entity
@Table(name = "equipment_assignments", indexes = {
    @Index(name = "idx_assignment_equipment_id", columnList = "equipment_id"),
    @Index(name = "idx_assignment_user_id", columnList = "user_id"),
    @Index(name = "idx_assignment_dates", columnList = "assigned_at,returned_at")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentAssignment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false, foreignKey = @ForeignKey(name = "fk_assignment_equipment"))
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_assignment_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", foreignKey = @ForeignKey(name = "fk_assignment_department"))
    private Department department;

    @Column(name = "assigned_at", nullable = false)
    private LocalDateTime assignedAt;

    @Column(name = "returned_at")
    private LocalDateTime returnedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_by_user_id", foreignKey = @ForeignKey(name = "fk_assignment_assigned_by"))
    private User assignedByUser;

    @Lob
    @Column(name = "notes")
    private String notes;
}
