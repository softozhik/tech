package com.yourname.equipment.license.dto;

import com.yourname.equipment.license.entity.SoftwareLicense;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO для создания лицензии на ПО.
 */
@Data
public class CreateSoftwareLicenseDto {

    @NotNull(message = "ID компании обязателен")
    private Long companyId;

    @NotNull(message = "Название ПО обязательно")
    @Size(max = 255, message = "Название ПО не должно превышать 255 символов")
    private String softwareName;

    @Size(max = 255, message = "Производитель не должен превышать 255 символов")
    private String softwareVendor;

    @Size(max = 100, message = "Версия не должна превышать 100 символов")
    private String version;

    @Size(max = 500, message = "Ключ лицензии не должен превышать 500 символов")
    private String licenseKey;

    @Size(max = 50, message = "Тип лицензии не должен превышать 50 символов")
    private String licenseType;

    private LocalDate purchaseDate;

    private BigDecimal purchasePrice;

    private LocalDate expiryDate;

    private Integer seatsTotal;

    private Integer seatsUsed;

    private Boolean autoRenewal;

    private SoftwareLicense.LicenseStatus status;

    private String notes;
}
