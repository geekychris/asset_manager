package com.example.artifactmanager.controller;

import com.example.artifactmanager.model.dto.ArtifactCreateRequest;
import com.example.artifactmanager.model.dto.ArtifactDto;
import com.example.artifactmanager.model.dto.ArtifactUpdateRequest;
import com.example.artifactmanager.service.ArtifactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/artifacts")
public class ArtifactController {

    private final ArtifactService artifactService;

    @Autowired
    public ArtifactController(ArtifactService artifactService) {
        this.artifactService = artifactService;
    }

    /**
     * Get all artifacts
     * @return List of all artifacts
     */
    @GetMapping
    public ResponseEntity<List<ArtifactDto>> getAllArtifacts() {
        List<ArtifactDto> artifacts = artifactService.getAllArtifacts();
        return ResponseEntity.ok(artifacts);
    }

    /**
     * Get artifact by ID
     * @param id The ID of the artifact to retrieve
     * @return The artifact if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArtifactDto> getArtifactById(@PathVariable Long id) {
        ArtifactDto artifact = artifactService.getArtifactById(id);
        return ResponseEntity.ok(artifact);
    }

    /**
     * Create a new artifact
     * @param request The artifact data
     * @return The newly created artifact
     */
    @PostMapping
    public ResponseEntity<ArtifactDto> createArtifact(@Valid @RequestBody ArtifactCreateRequest request) {
        ArtifactDto createdArtifact = artifactService.createArtifact(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArtifact);
    }

    /**
     * Update an existing artifact
     * @param id The ID of the artifact to update
     * @param request The updated artifact data
     * @return The updated artifact
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArtifactDto> updateArtifact(
            @PathVariable Long id,
            @Valid @RequestBody ArtifactUpdateRequest request) {
        ArtifactDto updatedArtifact = artifactService.updateArtifact(id, request);
        return ResponseEntity.ok(updatedArtifact);
    }

    /**
     * Delete an artifact by ID
     * @param id The ID of the artifact to delete
     * @return No content if successful
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtifact(@PathVariable Long id) {
        artifactService.deleteArtifact(id);
        return ResponseEntity.noContent().build();
    }
}

