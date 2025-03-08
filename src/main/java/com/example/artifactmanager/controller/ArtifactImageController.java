package com.example.artifactmanager.controller;

import com.example.artifactmanager.model.dto.ArtifactImageDto;
import com.example.artifactmanager.service.ArtifactImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ArtifactImageController {

    private final ArtifactImageService artifactImageService;

    @Autowired
    public ArtifactImageController(ArtifactImageService artifactImageService) {
        this.artifactImageService = artifactImageService;
    }

    /**
     * Get all images for a specific artifact
     * @param artifactId The ID of the artifact
     * @return List of images associated with the artifact
     */
    @GetMapping("/artifacts/{artifactId}/images")
    public ResponseEntity<List<ArtifactImageDto>> getImagesForArtifact(@PathVariable Long artifactId) {
        List<ArtifactImageDto> images = artifactImageService.getImagesForArtifact(artifactId);
        return ResponseEntity.ok(images);
    }

    /**
     * Get image by ID
     * @param id The ID of the image to retrieve
     * @return The image if found
     */
    @GetMapping("/images/{id}")
    public ResponseEntity<ArtifactImageDto> getImageById(@PathVariable Long id) {
        ArtifactImageDto image = artifactImageService.getImageById(id);
        return ResponseEntity.ok(image);
    }

    /**
     * Upload a new image for an artifact
     * @param artifactId The ID of the artifact
     * @param description The image description
     * @param file The image file to upload
     * @return The newly created image metadata
     * @throws IOException If there's an error handling the file
     */
    @PostMapping(value = "/artifacts/{artifactId}/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ArtifactImageDto> uploadImage(
            @PathVariable Long artifactId,
            @RequestParam(required = false) String description,
            @RequestParam("file") MultipartFile file) throws IOException {
        
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        ArtifactImageDto createdImage = artifactImageService.createImage(artifactId, description, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdImage);
    }

    /**
     * Update the description of an existing image
     * @param id The ID of the image to update
     * @param description The new description
     * @return The updated image metadata
     */
    @PutMapping("/images/{id}")
    public ResponseEntity<ArtifactImageDto> updateImage(
            @PathVariable Long id,
            @RequestParam String description) {
        ArtifactImageDto updatedImage = artifactImageService.updateImage(id, description);
        return ResponseEntity.ok(updatedImage);
    }

    /**
     * Delete an image by ID
     * @param id The ID of the image to delete
     * @return No content if successful
     * @throws IOException If there's an error deleting the file
     */
    @DeleteMapping("/images/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) throws IOException {
        artifactImageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}

