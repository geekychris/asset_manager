package com.example.artifactmanager.repository;

import com.example.artifactmanager.model.entity.ArtifactImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtifactImageRepository extends JpaRepository<ArtifactImage, Long> {
    List<ArtifactImage> findByArtifactId(Long artifactId);
}

