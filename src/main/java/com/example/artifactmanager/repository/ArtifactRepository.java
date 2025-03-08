package com.example.artifactmanager.repository;

import com.example.artifactmanager.model.entity.Artifact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
    Optional<Artifact> findBySerialNumber(String serialNumber);
    boolean existsBySerialNumber(String serialNumber);
}

