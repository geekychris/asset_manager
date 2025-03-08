package com.example.artifactmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactDto {
    private Long id;
    
    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotNull(message = "Date of manufacture is required")
    @PastOrPresent(message = "Date of manufacture must be in the past or present")
    private LocalDate dateOfManufacture;
    
    private String description;
    
    @NotNull(message = "Original price is required")
    @Positive(message = "Original price must be positive")
    private BigDecimal originalPrice;
    
    @NotNull(message = "Current value is required")
    @Positive(message = "Current value must be positive")
    private BigDecimal currentValue;
    
    @NotBlank(message = "Serial number is required")
    private String serialNumber;
    
    private List<ArtifactImageDto> images = new ArrayList<>();
}

