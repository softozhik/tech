package com.yourname.equipment.equipment.entity;

import com.yourname.equipment.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность категории техники.
 */
@Entity
@Table(name = "equipment_categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EquipmentCategory extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", length = 1000)
    private String description;
}
