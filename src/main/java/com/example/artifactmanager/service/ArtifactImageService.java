package com.example.artifactmanager.service;

import com.example.artifactmanager.exception.ResourceNotFoundException;
import com.example.artifactmanager.model.dto.ArtifactImageDto;
import com.example.artifactmanager.model.entity.Artifact;
import com.example.artifactmanager.model.entity.ArtifactImage;
import com.example.artifactmanager.repository.ArtifactImageRepository;
import com.example.artifactmanager.repository.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtifactImageService {

    private final ArtifactImageRepository artifactImageRepository;
    private final ArtifactRepository artifactRepository;
    private final StorageService storageService;

    @Autowired
    public ArtifactImageService(
            ArtifactImageRepository artifactImageRepository,
            ArtifactRepository artifactRepository,
            StorageService storageService) {
        this.artifactImageRepository = artifactImageRepository;
        this.artifactRepository = artifactRepository;
        this.storageService = storageService;
    }

    @Transactional(readOnly = true)
    public List<ArtifactImageDto> getImagesForArtifact(Long artifactId) {
        // Check if artifact exists
        if (!artifactRepository.existsById(artifactId)) {
            throw new ResourceNotFoundException("Artifact not found with id: " + artifactId);
        }
        
        return artifactImageRepository.findByArtifactId(artifactId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArtifactImageDto getImageById(Long id) {
        ArtifactImage image = findImageById(id);
        return convertToDto(image);
    }

    @Transactional
    public ArtifactImageDto createImage(Long artifactId, String description, MultipartFile file) throws IOException {
        // Find the artifact
        Artifact artifact = artifactRepository.findById(artifactId)
                .orElseThrow(() -> new ResourceNotFoundException("Artifact not found with id: " + artifactId));
        
        // Store the file and get the filename
        String storedFilename = storageService.store(file);
        
        // Create the image entity
        ArtifactImage image = ArtifactImage.builder()
                .fileName(file.getOriginalFilename())
                .description(description)
                .filePath(storedFilename)
                .artifact(artifact)
                .build();
        
        ArtifactImage savedImage = artifactImageRepository.save(image);
        return convertToDto(savedImage);
    }

    @Transactional
    public ArtifactImageDto updateImage(Long id, String description) {
        ArtifactImage image = findImageById(id);
        
        if (description != null) {
            image.setDescription(description);
        }
        
        ArtifactImage updatedImage = artifactImageRepository.save(image);
        return convertToDto(updatedImage);
    }

    @Transactional
    public void deleteImage(Long id) throws IOException {
        ArtifactImage image = findImageById(id);
        
        // Delete the file from storage
        storageService.delete(image.getFilePath());
        
        // Delete the database record
        artifactImageRepository.delete(image);
    }

    // Helper method to find an image by ID or throw exception
    private ArtifactImage findImageById(Long id) {
        return artifactImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found with id: " + id));
    }

    // Helper method to convert entity to DTO
    private ArtifactImageDto convertToDto(ArtifactImage image) {
        return ArtifactImageDto.builder()
                .id(image.getId())
                .fileName(image.getFileName())
                .description(image.getDescription())
                .filePath(image.getFilePath())
                .artifactId(image.getArtifact().getId())
                .build();
    }
}

