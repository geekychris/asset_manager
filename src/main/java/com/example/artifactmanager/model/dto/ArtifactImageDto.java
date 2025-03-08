package com.example.artifactmanager.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactImageDto {
    private Long id;
    
    @NotBlank(message = "File name is required")
    private String fileName;
    
    private String description;
    
    private String filePath;
    
    private Long artifactId;
}

