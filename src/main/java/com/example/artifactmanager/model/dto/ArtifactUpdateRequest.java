package com.example.artifactmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactUpdateRequest {
    private String manufacturer;
    private String name;
    
    @PastOrPresent(message = "Date of manufacture must be in the past or present")
    private LocalDate dateOfManufacture;
    
    private String description;
    
    @Positive(message = "Original price must be positive")
    private BigDecimal originalPrice;
    
    @Positive(message = "Current value must be positive")
    private BigDecimal currentValue;
}

