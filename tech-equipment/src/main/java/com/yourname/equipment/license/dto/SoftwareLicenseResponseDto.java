package com.yourname.equipment.license.dto;

import com.yourname.equipment.license.entity.SoftwareLicense;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO для ответа с данными лицензии на ПО.
 */
@Data
public class SoftwareLicenseResponseDto {
    private Long id;
    private Long companyId;
    private String companyName;
    private String softwareName;
    private String softwareVendor;
    private String version;
    private String licenseKey;
    private String licenseType;
    private LocalDate purchaseDate;
    private BigDecimal purchasePrice;
    private LocalDate expiryDate;
    private Integer seatsTotal;
    private Integer seatsUsed;
    private Boolean autoRenewal;
    private SoftwareLicense.LicenseStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
