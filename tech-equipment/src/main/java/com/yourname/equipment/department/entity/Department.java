package com.yourname.equipment.department.entity;

import com.yourname.equipment.common.entity.BaseEntity;
import com.yourname.equipment.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность отдела.
 */
@Entity
@Table(name = "departments", indexes = {
    @Index(name = "idx_department_company_id", columnList = "company_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, foreignKey = @ForeignKey(name = "fk_department_company"))
    private Company company;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_department_id", foreignKey = @ForeignKey(name = "fk_department_parent"))
    private Department parentDepartment;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "active", nullable = false)
    @Builder.Default
    private Boolean active = true;
}
