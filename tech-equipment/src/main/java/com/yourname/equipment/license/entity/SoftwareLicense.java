package com.yourname.equipment.license.entity;

import com.yourname.equipment.common.entity.BaseEntity;
import com.yourname.equipment.company.entity.Company;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Сущность лицензии на программное обеспечение.
 */
@Entity
@Table(name = "software_licenses", indexes = {
    @Index(name = "idx_license_company_id", columnList = "company_id"),
    @Index(name = "idx_license_expiry_date", columnList = "expiry_date")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SoftwareLicense extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false, foreignKey = @ForeignKey(name = "fk_license_company"))
    private Company company;

    @Column(name = "software_name", nullable = false)
    private String softwareName;

    @Column(name = "software_vendor")
    private String softwareVendor;

    @Column(name = "version", length = 100)
    private String version;

    @Column(name = "license_key", length = 500)
    private String licenseKey;

    @Column(name = "license_type", length = 50)
    private String licenseType;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "purchase_price", precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "seats_total", nullable = false)
    @Builder.Default
    private Integer seatsTotal = 1;

    @Column(name = "seats_used", nullable = false)
    @Builder.Default
    private Integer seatsUsed = 0;

    @Column(name = "auto_renewal", nullable = false)
    @Builder.Default
    private Boolean autoRenewal = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    @Builder.Default
    private LicenseStatus status = LicenseStatus.ACTIVE;

    @Lob
    @Column(name = "notes")
    private String notes;

    /**
     * Статусы лицензии.
     */
    public enum LicenseStatus {
        ACTIVE, EXPIRED, SUSPENDED, CANCELLED
    }
}
