package com.example.artifactmanager.service;

import com.example.artifactmanager.exception.ResourceNotFoundException;
import com.example.artifactmanager.exception.SerialNumberAlreadyExistsException;
import com.example.artifactmanager.model.dto.ArtifactCreateRequest;
import com.example.artifactmanager.model.dto.ArtifactDto;
import com.example.artifactmanager.model.dto.ArtifactImageDto;
import com.example.artifactmanager.model.dto.ArtifactUpdateRequest;
import com.example.artifactmanager.model.entity.Artifact;
import com.example.artifactmanager.repository.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtifactService {

    private final ArtifactRepository artifactRepository;

    @Autowired
    public ArtifactService(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }

    @Transactional(readOnly = true)
    public List<ArtifactDto> getAllArtifacts() {
        return artifactRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArtifactDto getArtifactById(Long id) {
        Artifact artifact = findArtifactById(id);
        return convertToDto(artifact);
    }

    @Transactional
    public ArtifactDto createArtifact(ArtifactCreateRequest request) {
        // Check if serial number already exists
        if (artifactRepository.existsBySerialNumber(request.getSerialNumber())) {
            throw new SerialNumberAlreadyExistsException("Serial number already exists: " + request.getSerialNumber());
        }

        Artifact artifact = Artifact.builder()
                .manufacturer(request.getManufacturer())
                .name(request.getName())
                .dateOfManufacture(request.getDateOfManufacture())
                .description(request.getDescription())
                .originalPrice(request.getOriginalPrice())
                .currentValue(request.getCurrentValue())
                .serialNumber(request.getSerialNumber())
                .build();

        Artifact savedArtifact = artifactRepository.save(artifact);
        return convertToDto(savedArtifact);
    }

    @Transactional
    public ArtifactDto updateArtifact(Long id, ArtifactUpdateRequest request) {
        Artifact artifact = findArtifactById(id);

        // Update only non-null fields
        if (request.getManufacturer() != null) {
            artifact.setManufacturer(request.getManufacturer());
        }
        if (request.getName() != null) {
            artifact.setName(request.getName());
        }
        if (request.getDateOfManufacture() != null) {
            artifact.setDateOfManufacture(request.getDateOfManufacture());
        }
        if (request.getDescription() != null) {
            artifact.setDescription(request.getDescription());
        }
        if (request.getOriginalPrice() != null) {
            artifact.setOriginalPrice(request.getOriginalPrice());
        }
        if (request.getCurrentValue() != null) {
            artifact.setCurrentValue(request.getCurrentValue());
        }

        Artifact updatedArtifact = artifactRepository.save(artifact);
        return convertToDto(updatedArtifact);
    }

    @Transactional
    public void deleteArtifact(Long id) {
        if (!artifactRepository.existsById(id)) {
            throw new ResourceNotFoundException("Artifact not found with id: " + id);
        }
        artifactRepository.deleteById(id);
    }

    // Helper method to find an artifact by ID or throw exception
    private Artifact findArtifactById(Long id) {
        return artifactRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artifact not found with id: " + id));
    }

    // Helper method to convert entity to DTO
    private ArtifactDto convertToDto(Artifact artifact) {
        ArtifactDto dto = ArtifactDto.builder()
                .id(artifact.getId())
                .manufacturer(artifact.getManufacturer())
                .name(artifact.getName())
                .dateOfManufacture(artifact.getDateOfManufacture())
                .description(artifact.getDescription())
                .originalPrice(artifact.getOriginalPrice())
                .currentValue(artifact.getCurrentValue())
                .serialNumber(artifact.getSerialNumber())
                .build();
                
        if (artifact.getImages() != null) {
            dto.setImages(artifact.getImages().stream()
                    .map(image -> ArtifactImageDto.builder()
                            .id(image.getId())
                            .fileName(image.getFileName())
                            .description(image.getDescription())
                            .filePath(image.getFilePath())
                            .artifactId(artifact.getId())
                            .build())
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }
}
